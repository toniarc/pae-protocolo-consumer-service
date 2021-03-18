package br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity;
// Generated 18 de mar de 2021 00:52:35 by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Protocolo generated by hbm2java
 */
@Entity
@Table(name = "protocolo", schema = "pae")
public class ProtocoloEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5502844345866853260L;
	
	private ProtocoloEntityId id;
	private Date dataProtocolo;
	private Long especieId;
	private Long assuntoId;
	private Long municipioId;
	private String origemDocumento;
	private String complemento;
	private String prioridade;
	private Long documentoProtocoladoId;
	private Long orgaoDestinoId;
	private Long localizacaoDestinoId;
	private Long numeroDocumento;
	private Integer anoDocumento;
	private String hashAnexos;
	private String hashAlgoritmo;
	private Long usuarioCadastroId;
	private String usuarioCadastroNome;
	private Long orgaoOrigemId;
	private String orgaoOrigemSigla;
	private Long localizacaoOrigemId;
	private Set<InteressadoEntity> interessados = new HashSet<InteressadoEntity>(0);
	private Set<AnexoEntity> anexos = new HashSet<AnexoEntity>(0);

	public ProtocoloEntity() {
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "ano", column = @Column(name = "ano", nullable = false)),
			@AttributeOverride(name = "numeroProtocolo", column = @Column(name = "numero_protocolo", nullable = false)) })
	public ProtocoloEntityId getId() {
		return this.id;
	}

	public void setId(ProtocoloEntityId id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_protocolo", nullable = false, length = 13)
	public Date getDataProtocolo() {
		return this.dataProtocolo;
	}

	public void setDataProtocolo(Date dataProtocolo) {
		this.dataProtocolo = dataProtocolo;
	}

	@Column(name = "especie_id", nullable = false)
	public Long getEspecieId() {
		return this.especieId;
	}

	public void setEspecieId(Long especieId) {
		this.especieId = especieId;
	}

	@Column(name = "assunto_id", nullable = false)
	public Long getAssuntoId() {
		return this.assuntoId;
	}

	public void setAssuntoId(Long assuntoId) {
		this.assuntoId = assuntoId;
	}

	@Column(name = "municipio_id", nullable = false)
	public Long getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "origem_documento", nullable = false, length = 20)
	public String getOrigemDocumento() {
		return this.origemDocumento;
	}

	public void setOrigemDocumento(String origemDocumento) {
		this.origemDocumento = origemDocumento;
	}

	@Column(name = "complemento", length = 200)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(name = "prioridade", nullable = false, length = 20)
	public String getPrioridade() {
		return this.prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	@Column(name = "documento_protocolado_id", nullable = false)
	public Long getDocumentoProtocoladoId() {
		return this.documentoProtocoladoId;
	}

	public void setDocumentoProtocoladoId(Long documentoProtocoladoId) {
		this.documentoProtocoladoId = documentoProtocoladoId;
	}

	@Column(name = "orgao_destino_id")
	public Long getOrgaoDestinoId() {
		return this.orgaoDestinoId;
	}

	public void setOrgaoDestinoId(Long orgaoDestinoId) {
		this.orgaoDestinoId = orgaoDestinoId;
	}

	@Column(name = "localizacao_destino_id")
	public Long getLocalizacaoDestinoId() {
		return this.localizacaoDestinoId;
	}

	public void setLocalizacaoDestinoId(Long localizacaoDestinoId) {
		this.localizacaoDestinoId = localizacaoDestinoId;
	}

	@Column(name = "numero_documento", nullable = false)
	public Long getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@Column(name = "ano_documento", nullable = false)
	public Integer getAnoDocumento() {
		return this.anoDocumento;
	}

	public void setAnoDocumento(Integer anoDocumento) {
		this.anoDocumento = anoDocumento;
	}

	@Column(name = "hash_anexos", nullable = false, length = 200)
	public String getHashAnexos() {
		return this.hashAnexos;
	}

	public void setHashAnexos(String hashAnexos) {
		this.hashAnexos = hashAnexos;
	}

	@Column(name = "hash_algoritmo", length = 10)
	public String getHashAlgoritmo() {
		return this.hashAlgoritmo;
	}

	public void setHashAlgoritmo(String hashAlgoritmo) {
		this.hashAlgoritmo = hashAlgoritmo;
	}

	@Column(name = "usuario_cadastro_id", nullable = false)
	public Long getUsuarioCadastroId() {
		return this.usuarioCadastroId;
	}

	public void setUsuarioCadastroId(Long usuarioCadastroId) {
		this.usuarioCadastroId = usuarioCadastroId;
	}

	@Column(name = "usuario_cadastro_nome", nullable = false, length = 100)
	public String getUsuarioCadastroNome() {
		return this.usuarioCadastroNome;
	}

	public void setUsuarioCadastroNome(String usuarioCadastroNome) {
		this.usuarioCadastroNome = usuarioCadastroNome;
	}

	@Column(name = "orgao_origem_id", nullable = false)
	public Long getOrgaoOrigemId() {
		return this.orgaoOrigemId;
	}

	public void setOrgaoOrigemId(Long orgaoOrigemId) {
		this.orgaoOrigemId = orgaoOrigemId;
	}

	@Column(name = "orgao_origem_sigla", length = 20)
	public String getOrgaoOrigemSigla() {
		return this.orgaoOrigemSigla;
	}

	public void setOrgaoOrigemSigla(String orgaoOrigemSigla) {
		this.orgaoOrigemSigla = orgaoOrigemSigla;
	}

	@Column(name = "localizacao_origem_id")
	public Long getLocalizacaoOrigemId() {
		return this.localizacaoOrigemId;
	}

	public void setLocalizacaoOrigemId(Long localizacaoOrigemId) {
		this.localizacaoOrigemId = localizacaoOrigemId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "protocolo", cascade = CascadeType.ALL)
	public Set<InteressadoEntity> getInteressados() {
		return this.interessados;
	}

	public void setInteressados(Set<InteressadoEntity> interessados) {
		this.interessados = interessados;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "protocolo", cascade = CascadeType.ALL)
	public Set<AnexoEntity> getAnexos() {
		return this.anexos;
	}

	public void setAnexos(Set<AnexoEntity> anexos) {
		this.anexos = anexos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProtocoloEntity other = (ProtocoloEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
