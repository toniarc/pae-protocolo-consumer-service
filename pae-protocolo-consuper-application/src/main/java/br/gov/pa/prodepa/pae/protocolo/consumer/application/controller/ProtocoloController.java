package br.gov.pa.prodepa.pae.protocolo.consumer.application.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

	@Autowired
	private Publisher<Message<ProtocoloResponseDto>> jmsReactiveSource;
	
	public ProtocoloDto jsonToProtocoloDto(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ProtocoloDto dto = objectMapper.readValue(json, ProtocoloDto.class);
			return dto;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProtocoloResponseDto> getPatientAlerts() {
        return Flux.from(jmsReactiveSource)
        		.log()
        		.map( msg -> msg.getPayload());
        		//.filter( p -> {
        		//	return p.getUsuarioCadastro().getId().equals(userId);
        		//});
    }
}
