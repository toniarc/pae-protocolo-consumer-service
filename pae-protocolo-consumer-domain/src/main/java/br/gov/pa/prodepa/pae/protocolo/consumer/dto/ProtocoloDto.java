package br.gov.pa.prodepa.pae.protocolo.consumer.dto;

import java.io.Serializable;
import java.util.List;

import br.gov.pa.prodepa.nucleopa.client.MunicipioBasicDto;
import br.gov.pa.prodepa.pae.documento.client.DocumentoBasicDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.PessoaFisica;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.PessoaJuridica;
import br.gov.pa.prodepa.pae.suporte.client.AssuntoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.EspecieBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.LocalizacaoBasicDto;
import br.gov.pa.prodepa.pae.suporte.client.OrgaoPaeBasicDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocoloDto implements Serializable {
	
	private static final long serialVersionUID = 2328859682092032647L;
	
	private EspecieBasicDto especie;
	private AssuntoBasicDto assunto;
	private MunicipioBasicDto municipio;
	private OrigemDocumento origemDocumento;
	private String complemento;
	private Prioridade prioridade;
	private DocumentoBasicDto documentoProtocolado;
	private OrgaoPaeBasicDto orgaoDestino;
	private LocalizacaoBasicDto localizacaoDestino;
	
	private Usuario usuarioCadastro;
	
	private OrgaoPaeBasicDto orgaoOrigem;
	private LocalizacaoBasicDto localizacaoOrigem;
	
	//interessados
	private List<PessoaFisica> interessadosPessoasFisicas;
	private List<PessoaJuridica> interessadosPessoasJuricas;
	private List<OrgaoPaeBasicDto> orgaosInteressados;
	private List<LocalizacaoBasicDto> localizacoesInteressadas;
	private List<Usuario> assinantes;
}