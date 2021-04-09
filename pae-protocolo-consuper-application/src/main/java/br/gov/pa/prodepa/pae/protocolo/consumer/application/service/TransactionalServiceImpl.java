package br.gov.pa.prodepa.pae.protocolo.consumer.application.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import br.gov.pa.prodepa.pae.protocolo.consumer.service.TransactionalService;
import br.gov.pa.prodepa.pae.protocolo.consumer.service.TransactionalStatus;

@Component
public class TransactionalServiceImpl implements TransactionalService {

	private final TransactionTemplate transactionTemplate;

	@Autowired
	public TransactionalServiceImpl(PlatformTransactionManager transactionManager) {
		super();
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}

	@Override
	public <R> R executarEmTransacaoSeparada(Function<TransactionalStatus, R> action) {
		return transactionTemplate.execute(status -> {
			boolean newTransaction = status.isNewTransaction();
			return action.apply(new TransactionalStatus(newTransaction));
		});
	}
	
}
