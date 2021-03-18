package br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity;
// Generated 18 de mar de 2021 00:52:35 by Hibernate Tools 5.2.12.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Assinatura generated by hbm2java
 */
@Entity
@Table(name = "assinatura", schema = "pae")
public class AssinaturaEntity implements java.io.Serializable {

	private int id;
	private AnexoEntity anexo;
	private Long usuarioAssinanteId;
	private String tipoAssinatura;
	private Date dataRequisicao;
	private Long usuarioRequisitanteId;
	private byte[] dadosAssinatura;
	private Boolean assinado;

	public AssinaturaEntity() {
	}

	public AssinaturaEntity(int id, Long usuarioAssinanteId, String tipoAssinatura, Long usuarioRequisitanteId) {
		this.id = id;
		this.usuarioAssinanteId = usuarioAssinanteId;
		this.tipoAssinatura = tipoAssinatura;
		this.usuarioRequisitanteId = usuarioRequisitanteId;
	}

	public AssinaturaEntity(int id, AnexoEntity anexo, Long usuarioAssinanteId, String tipoAssinatura, Date dataRequisicao,
			Long usuarioRequisitanteId, byte[] dadosAssinatura, Boolean assinado) {
		this.id = id;
		this.anexo = anexo;
		this.usuarioAssinanteId = usuarioAssinanteId;
		this.tipoAssinatura = tipoAssinatura;
		this.dataRequisicao = dataRequisicao;
		this.usuarioRequisitanteId = usuarioRequisitanteId;
		this.dadosAssinatura = dadosAssinatura;
		this.assinado = assinado;
	}

	@Id
	@SequenceGenerator(name="ASSINATURA_ID_GENERATOR", sequenceName="pae.sq_assinatura", allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSINATURA_ID_GENERATOR")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id")
	public AnexoEntity getAnexo() {
		return this.anexo;
	}

	public void setAnexo(AnexoEntity anexo) {
		this.anexo = anexo;
	}

	@Column(name = "usuario_assinante_id", nullable = false)
	public Long getUsuarioAssinanteId() {
		return this.usuarioAssinanteId;
	}

	public void setUsuarioAssinanteId(Long usuarioAssinanteId) {
		this.usuarioAssinanteId = usuarioAssinanteId;
	}

	@Column(name = "tipo_assinatura", length = 20)
	public String getTipoAssinatura() {
		return this.tipoAssinatura;
	}

	public void setTipoAssinatura(String tipoAssinatura) {
		this.tipoAssinatura = tipoAssinatura;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_requisicao", length = 13)
	public Date getDataRequisicao() {
		return this.dataRequisicao;
	}

	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	@Column(name = "usuario_requisitante_id", nullable = false)
	public Long getUsuarioRequisitanteId() {
		return this.usuarioRequisitanteId;
	}

	public void setUsuarioRequisitanteId(Long usuarioRequisitanteId) {
		this.usuarioRequisitanteId = usuarioRequisitanteId;
	}

	@Column(name = "dados_assinatura")
	public byte[] getDadosAssinatura() {
		return this.dadosAssinatura;
	}

	public void setDadosAssinatura(byte[] dadosAssinatura) {
		this.dadosAssinatura = dadosAssinatura;
	}

	@Column(name = "assinado")
	public Boolean getAssinado() {
		return this.assinado;
	}

	public void setAssinado(Boolean assinado) {
		this.assinado = assinado;
	}

}
