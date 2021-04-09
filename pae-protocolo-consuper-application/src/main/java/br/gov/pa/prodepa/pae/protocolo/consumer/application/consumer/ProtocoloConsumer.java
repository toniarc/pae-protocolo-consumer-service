package br.gov.pa.prodepa.pae.protocolo.consumer.application.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.client.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.MessagingConsumerService;

@Component
public class ProtocoloConsumer implements MessagingConsumerService {

	@Autowired
	private ProtocoloApplicationService service;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "protocolar-documento")
	public void processMessage(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		ProtocoloDto dto;
		try {
			dto = objectMapper.readValue(json, ProtocoloDto.class);
			service.protocolarDocumento(dto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendNotification(ProtocoloResponseDto response) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(response);
			jmsTemplate.send("protocolar-documento-response", new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage msg = session.createTextMessage(json);
					//msg.setJMSCorrelationID(json);
					return msg;
				}
			});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void enviarParaFilaGeracaoPdf(ProtocoloDto protocoloDto) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(protocoloDto);
			jmsTemplate.send("protocolo.gerar-pdf", session -> {
				return session.createTextMessage(json);
			});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void enviarNotificacao(ProtocoloResponseDto protocoloSalvo) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(protocoloSalvo);
			jmsTemplate.send("notificacao", session -> {
				return session.createTextMessage(json);
			});
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
