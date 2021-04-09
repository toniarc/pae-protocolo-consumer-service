package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import java.util.function.Function;


public interface TransactionalService {

	<R> R executarEmTransacaoSeparada(Function<TransactionalStatus,R> action);

}
