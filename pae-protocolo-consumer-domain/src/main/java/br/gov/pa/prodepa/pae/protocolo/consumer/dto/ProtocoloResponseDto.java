package br.gov.pa.prodepa.pae.protocolo.consumer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProtocoloResponseDto {

	private Integer ano;
	private Long numero;
	
}
