package br.gov.pa.prodepa.pae.protocolo.consumer.config;

import javax.jms.ConnectionFactory;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.pae.protocolo.consumer.application.service.ProtocoloApplicationService;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;

@EnableJms
@Configuration
public class JmsConfig {

	@Autowired
    private ConnectionFactory connectionFactory;
	
	@Autowired
	private ProtocoloApplicationService service;
	
	//@Transformer()
	public ProtocoloDto jsonToProtocoloDto(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, ProtocoloDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Bean
    public Publisher<Message<ProtocoloResponseDto>> jmsReactiveSource() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.connectionFactory)
                        .destination("protocolar-documento")
                	)
                .transform(Transformers.fromJson(ProtocoloDto.class))
                .transform( protocoloDto -> service.protocolarDocumento((ProtocoloDto) protocoloDto))
                .channel(MessageChannels.queue())
                .log(LoggingHandler.Level.DEBUG)
                .log()
                .toReactivePublisher();
    }
}
