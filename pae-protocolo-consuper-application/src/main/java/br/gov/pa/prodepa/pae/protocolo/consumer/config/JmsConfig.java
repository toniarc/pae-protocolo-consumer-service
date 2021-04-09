package br.gov.pa.prodepa.pae.protocolo.consumer.config;

import javax.jms.ConnectionFactory;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.messaging.Message;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;

@EnableJms
@Configuration
public class JmsConfig {

	@Autowired
    private ConnectionFactory connectionFactory;
	
	@Bean
    public JmsListenerContainerFactory<?> queueConnectionFactory(ConnectionFactory connectionFactory,
                                                                 DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicConnectionFactory(ConnectionFactory connectionFactory,
                                                                 DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
	
	@Bean
    public Publisher<Message<ProtocoloResponseDto>> jmsReactiveSource() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.connectionFactory)
                        .destination("notificacao")
                	)
                .transform(Transformers.fromJson(ProtocoloResponseDto.class))
                .filter( m -> {
                	System.out.println(m);
                	return ((ProtocoloResponseDto) m).getUsuarioId().equals(3199L);
                })
                .channel(MessageChannels.queue())
                .log(LoggingHandler.Level.DEBUG)
                .log()
                .toReactivePublisher();
    }
}
