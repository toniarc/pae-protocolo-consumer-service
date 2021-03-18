package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Anexo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Assinatura;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Interessado;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.PessoaFisica;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.PessoaJuridica;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.ProtocoloId;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.SequencialProtolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.TipoInteressado;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.Usuario;
import br.gov.pa.prodepa.pae.protocolo.consumer.exception.SequencialProtocoloExistenteException;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.MessagingConsumerService;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.ProtocoloRepository;
import br.gov.pa.prodepa.pae.suporte.client.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.OrgaoPaeBasicDto;

public class ProtocoloDomainServce implements ProtocoloService {

	private MessagingConsumerService messagingService;
	
	private final ProtocoloRepository protocoloRepository;
	
	private final TransactionalService transactionalService;
	
	public ProtocoloDomainServce(ProtocoloRepository protocoloRepository, TransactionalService transactionalService) {
		super();
		//this.messagingService = messagingService;
		this.protocoloRepository = protocoloRepository;
		this.transactionalService = transactionalService;
	}

	public ProtocoloResponseDto protocolarDocumento(ProtocoloDto protocoloDto) {

		Date now = new Date();
		
		SequencialProtolo sequencialProtolo = transactionalService.executarEmTransacaoSeparada( status -> buscarProximoSequencial());
		
		
		Protocolo protocolo = new Protocolo().builder()
			.id(ProtocoloId.builder()
					.ano(sequencialProtolo.getAno())
					.numeroProtocolo(sequencialProtolo.getSequencial())
					.build())
			.especieId(protocoloDto.getEspecie().getId())
			.assuntoId(protocoloDto.getAssunto().getId())
			.municipioId(protocoloDto.getMunicipio().getId())
			.origemDocumento(protocoloDto.getOrigemDocumento().name())
			.complemento(protocoloDto.getComplemento())
			.prioridade(protocoloDto.getPrioridade().name())
			.documentoProtocoladoId(protocoloDto.getDocumentoProtocolado().getId())
			.anoDocumento(protocoloDto.getDocumentoProtocolado().getAno())
			.numeroDocumento(protocoloDto.getDocumentoProtocolado().getSequencial())
			.orgaoDestinoId(protocoloDto.getOrgaoDestino().getId())
			.localizacaoDestinoId(protocoloDto.getLocalizacaoDestino() != null ? protocoloDto.getLocalizacaoDestino().getId() : null)
			.usuarioCadastroId(protocoloDto.getUsuarioCadastro().getId())
			.usuarioCadastroNome(protocoloDto.getUsuarioCadastro().getNome())
			.orgaoOrigemId(protocoloDto.getOrgaoOrigem().getId())
			.localizacaoOrigemId(protocoloDto.getLocalizacaoOrigem().getId())
			.dataProtocolo(now)
			.interessados(getInteressados(protocoloDto))
			.anexos(getAnexos(protocoloDto))
			.hashAlgoritmo("sha256")//TODO alterar
			.hashAnexos("dfdfdf")//TODO alterar
			.build();
		
		return protocoloRepository.salvar(protocolo);
	}

	private Set<Anexo> getAnexos(ProtocoloDto protocoloDto) {
		Set<Anexo> anexos = new HashSet<Anexo>(1);
		Anexo anexo = Anexo.builder()
				.anexadoEm(new Date())
				.assinadoPorTodos(false)
				.conteudo(protocoloDto.getDocumentoProtocolado().getConteudo())
				.documentoInicial(true)
				.hashAlgoritmo("sha256") //TODO alterar
				.hashArquivo("dfdfdfdfd") //TODO alterar
				.quantidadeAssinaturas(0)
				.quantidadePaginas(1) //TODO alterar
				.s3StorageId(UUID.randomUUID().toString())
				.sequencial(1L) //TODO alterar
				.tamanhoArquivoMb(1)//TODO alterar
				.assinaturas(getAssinaturas(protocoloDto))
				.build();
		anexos.add(anexo);
		return anexos;
	}
	
	private Set<Assinatura> getAssinaturas(ProtocoloDto protocoloDto){
		Set<Assinatura> assinaturas = new HashSet<Assinatura>(protocoloDto.getAssinantes().size());
		if(protocoloDto.getAssinantes() != null) {
			for(Usuario u : protocoloDto.getAssinantes()) {
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
		return assinaturas;
	}
	
	private Set<Interessado> getInteressados(ProtocoloDto protocoloDto){
		Set<Interessado> interessados = new HashSet<Interessado>();
		if(protocoloDto.getInteressadosPessoasFisicas() != null) {
			for(PessoaFisica pf : protocoloDto.getInteressadosPessoasFisicas()) {
				Interessado interessado = Interessado.builder()
				.nome(pf.getNome())
				.pessoaId(pf.getId())
				.tipoInteressado(TipoInteressado.PESSOA_FISICA)
				.build();
				interessados.add(interessado);
			}
		}
		
		if(protocoloDto.getInteressadosPessoasJuricas() != null) {
			for(PessoaJuridica pf : protocoloDto.getInteressadosPessoasJuricas()) {
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
			for(LocalizacaoBasicDto pf : protocoloDto.getLocalizacoesInteressadas()) {
				Interessado interessado = Interessado.builder()
				.pessoaId(pf.getId())
				.nome(pf.getNome())
				.tipoInteressado(TipoInteressado.SETOR)
				.build();
				interessados.add(interessado);
			}
		}
		return interessados;
	}
	
	public SequencialProtolo buscarProximoSequencial() {
		
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
		
		return sequenciaDocumento;
	}
	
	public SequencialProtolo criarNovaSequencia() throws SequencialProtocoloExistenteException {
		int ano = LocalDate.now().getYear();
		Long sequencial = 1L;
		protocoloRepository.criarSequencia(ano, sequencial);
		return new SequencialProtolo(ano, sequencial);
	}
}