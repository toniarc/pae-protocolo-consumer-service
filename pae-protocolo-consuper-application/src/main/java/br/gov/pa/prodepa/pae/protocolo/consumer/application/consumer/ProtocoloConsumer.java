package br.gov.pa.prodepa.pae.protocolo.consumer.application.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.consumer.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;

@Component
public class ProtocoloConsumer {

	@Autowired
	private ProtocoloApplicationService service;
	
	//@JmsListener(destination = "protocolar-documento")
	public ProtocoloDto processMessage(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		ProtocoloDto dto;
		try {
			dto = objectMapper.readValue(json, ProtocoloDto.class);
			service.protocolarDocumento(dto);
			return dto;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
