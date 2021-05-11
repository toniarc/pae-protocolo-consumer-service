package br.gov.pa.prodepa.pae.protocolo.consumer.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.ProtocoloService;

@Component
public class ProtocoloApplicationService {
	
	@Autowired
	private ProtocoloService service;

	public void protocolarDocumento(ProtocoloDto dto) {
		service.protocolarDocumento(dto);
	}
}
