package br.gov.pa.prodepa.pae.protocolo.consumer.application.fabric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.consumer.port.MessagingConsumerService;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.ProtocoloRepository;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.Html2PdfService;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.ObjectStorageService;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.ProtocoloDomainServce;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.ProtocoloService;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.TransactionalService;

@Component
public class DomainRegistry {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public ProtocoloService criarProtocoloService() {
		return new ProtocoloDomainServce(
				applicationContext.getBean(ProtocoloRepository.class),
				applicationContext.getBean(TransactionalService.class),
				applicationContext.getBean(MessagingConsumerService.class),
				applicationContext.getBean(Html2PdfService.class),
				applicationContext.getBean(ObjectStorageService.class));
	}
}
