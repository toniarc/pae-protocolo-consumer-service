package br.gov.pa.prodepa.pae.protocolo.consumer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interessado {

	private int id;
	private Protocolo protocolo;
	private Long pessoaId;
	private String nome;
	private TipoInteressado tipoInteressado;
	private String sigla;
	private Long setorId;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Interessado other = (Interessado) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
