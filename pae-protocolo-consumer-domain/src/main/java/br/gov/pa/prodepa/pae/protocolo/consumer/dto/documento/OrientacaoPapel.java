package br.gov.pa.prodepa.pae.protocolo.consumer.dto.documento;

public enum OrientacaoPapel {

	LANDSCAPE("Landscape"), PORTRAIT("Portrait");
	
	private OrientacaoPapel(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}
}
