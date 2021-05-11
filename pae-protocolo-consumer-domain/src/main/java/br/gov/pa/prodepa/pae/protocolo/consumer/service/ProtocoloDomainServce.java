package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.StopWatch;

import br.gov.pa.prodepa.pae.common.domain.dto.UsuarioDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Anexo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Assinatura;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Interessado;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.ProtocoloId;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.SequencialProtolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.TipoInteressado;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.documento.ModeloEstruturaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.nucleopa.PessoaFisicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.nucleopa.PessoaJuridicaBasicDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.suporte.LocalizacaoComEnderecoDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.suporte.OrgaoPaeBasicDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.exception.SequencialProtocoloExistenteException;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.MessagingConsumerService;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.ProtocoloRepository;
import br.gov.pa.prodepa.pae.protocolo.consumer.validator.ProtocoloUtil;

public class ProtocoloDomainServce implements ProtocoloService {

	private MessagingConsumerService messagingService;
	
	private final ProtocoloRepository protocoloRepository;
	
	private final TransactionalService transactionalService;

	private final Html2PdfService html2PdfService;

	private final ObjectStorageService storageService;
	
	public ProtocoloDomainServce(
			ProtocoloRepository protocoloRepository, 
			TransactionalService transactionalService, 
			MessagingConsumerService messagingService,
			Html2PdfService html2PdfService, 
			ObjectStorageService storageService ) {
		super();
		this.messagingService = messagingService;
		this.protocoloRepository = protocoloRepository;
		this.transactionalService = transactionalService;
		this.html2PdfService = html2PdfService;
		this.storageService = storageService;
	}

	public void protocolarDocumento(ProtocoloDto protocoloDto) {

		Date now = new Date();
		
		SequencialProtolo sequencialProtolo = transactionalService.executarEmTransacaoSeparada( status -> buscarProximoSequencial());
		
		ProtocoloId id = ProtocoloId.builder()
		.ano(sequencialProtolo.getAno())
		.numeroProtocolo(sequencialProtolo.getSequencial())
		.build();
		
		protocoloDto.setAnoProtocolo(id.getAno());
		protocoloDto.setNumeroProtocolo(id.getNumeroProtocolo());
		protocoloDto.setDataProtocolo(now);
		
		Protocolo protocolo = Protocolo.builder()
			.id(id)
			.especieId(protocoloDto.getEspecie().getId())
			.assuntoId(protocoloDto.getAssunto().getId())
			.municipioId(protocoloDto.getMunicipio().getId())
			.origemDocumento(protocoloDto.getOrigemDocumento().name())
			.complemento(protocoloDto.getComplemento())
			.prioridade(protocoloDto.getPrioridade().name())
			.documentoProtocoladoId(protocoloDto.getDocumentoProtocolado().getId())
			.anoDocumento(protocoloDto.getDocumentoProtocolado().getAno())
			.numeroDocumento(protocoloDto.getDocumentoProtocolado().getNumero())
			.orgaoDestinoId(protocoloDto.getOrgaoDestino().getId())
			.localizacaoDestinoId(protocoloDto.getLocalizacaoDestino() != null ? protocoloDto.getLocalizacaoDestino().getId() : null)
			.usuarioCadastroId(protocoloDto.getUsuarioCadastro().getId())
			.usuarioCadastroNome(protocoloDto.getUsuarioCadastro().getNome())
			.orgaoOrigemId(protocoloDto.getOrgaoOrigem().getId())
			.localizacaoOrigemId(protocoloDto.getLocalizacaoOrigem().getId())
			.dataProtocolo(now)
			.interessados(gerarInteressados(protocoloDto))
			.anexos(gerarAnexos(protocoloDto))
			.hashAlgoritmo("sha256")
			.build();
		
		Anexo anexo = protocolo.getAnexos().get(0);
		protocolo.setHashAnexos(anexo.getHashArquivo());
		
		ProtocoloResponseDto protocoloSalvo = protocoloRepository.salvar(protocolo);
		protocoloSalvo.setUsuarioId(protocoloDto.getUsuarioCadastro().getId());
		protocoloSalvo.setFileStorageId(anexo.getS3StorageId());
		
		messagingService.enviarNotificacao(protocoloSalvo);
		
	}
	
	private List<Anexo> gerarAnexos(ProtocoloDto protocoloDto) {
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		List<Anexo> anexos = new ArrayList<Anexo>(1);

		protocoloDto.getDocumento().setConteudo(ProtocoloUtil.substituirCamposDinamicosProtocolo(protocoloDto.getDocumento().getConteudo(), protocoloDto));
		
		ModeloEstruturaBasicDto modeloEstrutura = protocoloDto.getDocumento().getModeloConteudo().getModeloEstrutura();
		modeloEstrutura.setCabecalho(ProtocoloUtil.substituirCamposDinamicosProtocolo(modeloEstrutura.getCabecalho(), protocoloDto));
		modeloEstrutura.setRodape(ProtocoloUtil.substituirCamposDinamicosProtocolo(modeloEstrutura.getRodape(), protocoloDto));
		
		byte[] pdf = html2PdfService.gerarPdf(protocoloDto.getDocumento().getConteudo(), modeloEstrutura);
		
		Anexo anexo = Anexo.builder()
				.anexadoEm(new Date())
				.assinadoPorTodos(false)
				.conteudo(protocoloDto.getDocumento().getConteudo())
				.documentoInicial(true)
				.hashAlgoritmo("SHA-256") 
				.hashArquivo(gerarHash(pdf))
				.quantidadeAssinaturas(0)
				.quantidadePaginas(1) //TODO alterar
				.s3StorageId(UUID.randomUUID().toString())
				.sequencial(1L) 
				.tamanhoArquivoMb(getFileSizeInMB(pdf))
				.assinaturas(gerarAssinaturas(protocoloDto))
				.build();
		anexos.add(anexo);
		
		storageService.putObject(anexo.getS3StorageId(), pdf, "image/png");
		
		watch.stop();
		System.out.println("Gerar Anexo: " + watch.getTotalTimeMillis()  + " milisegundos");
		return anexos;
	}
	
	private Set<Assinatura> gerarAssinaturas(ProtocoloDto protocoloDto){
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		Set<Assinatura> assinaturas = new HashSet<Assinatura>(protocoloDto.getAssinantes().size());
		if(protocoloDto.getAssinantes() != null) {
			for(UsuarioDto u : protocoloDto.getAssinantes()) {
				Assinatura assinatura = Assinatura.builder()
					.assinado(false)
					.dadosAssinatura(null)
					.tipoAssinatura(null)
					.dataRequisicao(new Date())
					.usuarioAssinanteId(u.getId())
					.usuarioRequisitanteId(protocoloDto.getUsuarioCadastro().getId())
					.build();
				assinaturas.add(assinatura);
			}
		}
		
		watch.stop();
		System.out.println("Gerar Assinaturas: " + watch.getTotalTimeMillis()  + " milisegundos");
		return assinaturas;
	}
	
	private List<Interessado> gerarInteressados(ProtocoloDto protocoloDto){
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		List<Interessado> interessados = new ArrayList<Interessado>();
		if(protocoloDto.getInteressadosPessoasFisicas() != null) {
			for(PessoaFisicaBasicDto pf : protocoloDto.getInteressadosPessoasFisicas()) {
				Interessado interessado = Interessado.builder()
				.nome(pf.getNome())
				.pessoaId(pf.getId())
				.tipoInteressado(TipoInteressado.PESSOA_FISICA)
				.build();
				interessados.add(interessado);
			}
		}
		
		if(protocoloDto.getInteressadosPessoasJuricas() != null) {
			for(PessoaJuridicaBasicDto pf : protocoloDto.getInteressadosPessoasJuricas()) {
				Interessado interessado = Interessado.builder()
				.nome(pf.getNome())
				.pessoaId(pf.getId())
				.sigla(pf.getNome())
				.tipoInteressado(TipoInteressado.PESSOA_JURIDICA)
				.build();
				interessados.add(interessado);
			}
		}
		
		if(protocoloDto.getOrgaosInteressados() != null) {
			for(OrgaoPaeBasicDto pf : protocoloDto.getOrgaosInteressados()) {
				Interessado interessado = Interessado.builder()
				.pessoaId(pf.getId())
				.sigla(pf.getSigla())
				.tipoInteressado(TipoInteressado.ORGAO)
				.build();
				interessados.add(interessado);
			}
		}
		
		if(protocoloDto.getLocalizacoesInteressadas() != null) {
			for(LocalizacaoComEnderecoDto pf : protocoloDto.getLocalizacoesInteressadas()) {
				Interessado interessado = Interessado.builder()
				.pessoaId(pf.getId())
				.nome(pf.getNome())
				.tipoInteressado(TipoInteressado.SETOR)
				.build();
				interessados.add(interessado);
			}
		}
		
		watch.stop();
		System.out.println("Gerar Interessados: " + watch.getTotalTimeMillis()  + " milisegundos");
		
		return interessados;
	}
	
	public SequencialProtolo buscarProximoSequencial() {
	
		StopWatch watch = new StopWatch();
		watch.start();
		
		int ano = LocalDate.now().getYear();
		
		SequencialProtolo sequenciaDocumento = transactionalService.executarEmTransacaoSeparada(status -> {
			if(!status.isNewTransaction()) {
				throw new RuntimeException("O metodo 'buscarProximoSequencial' deve executar em uma transacao separada.");
			}
			
			/**
			 * executa uma consulta usando 'SELEC .. FOR UPDATE' definir um lock no registro em questao
			 * e, se retornar alguma tupla, incrementa o sequencial e em faz um update da sequencia incrementada.
			 * Por fim, retorna o valor atual da sequencia
			 */
			Long sequencial = protocoloRepository.buscarProximoSequencial(ano);
			return new SequencialProtolo(ano, sequencial);
		});
		
		if(sequenciaDocumento == null || sequenciaDocumento.getSequencial() == null) {
			try {
				return transactionalService.executarEmTransacaoSeparada(status -> criarNovaSequencia());
			} catch (SequencialProtocoloExistenteException e) {
				/**
				 * caso ocorra de nesse meio tempo outra thread cadastrar a mesma sequence, entao a aplicacao 
				 * apenas busca novamente usando os mesmo parametros
				 */
				return transactionalService.executarEmTransacaoSeparada(status -> {
					Long sequencial = protocoloRepository.buscarProximoSequencial(ano);
					return new SequencialProtolo(ano, sequencial);
				});
			}
		}
		
		watch.stop();
		System.out.println("Buscar Sequencial: " + watch.getTotalTimeMillis()  + " milisegundos");
		
		return sequenciaDocumento;
	}
	
	public SequencialProtolo criarNovaSequencia() throws SequencialProtocoloExistenteException {
		int ano = LocalDate.now().getYear();
		Long sequencial = 1L;
		protocoloRepository.criarSequencia(ano, sequencial);
		return new SequencialProtolo(ano, sequencial);
	}
	
	private String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	private float getFileSizeInMB(byte[] file) {
		int fileSizeInBytes = file.length;
		Float fileSizeInKB = (float) (fileSizeInBytes / 1024);
		Float fileSizeInMB = fileSizeInKB / 1024;
		
		BigDecimal bigDecimal = new BigDecimal(Float.toString(fileSizeInMB));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
	}
	
	private String gerarHash(byte[] file) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		byte[] hash = digest.digest(file);
		return bytesToHex(hash);
	}
	
	
}
