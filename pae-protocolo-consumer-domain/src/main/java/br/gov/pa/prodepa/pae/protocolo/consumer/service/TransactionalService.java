package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import org.springframework.transaction.support.TransactionCallback;

public interface TransactionalService {

	<T> T executarEmTransacaoSeparada(TransactionCallback<T> action);

}
