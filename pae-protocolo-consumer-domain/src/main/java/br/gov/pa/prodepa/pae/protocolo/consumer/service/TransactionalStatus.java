package br.gov.pa.prodepa.pae.protocolo.consumer.service;

public class TransactionalStatus {

	private boolean newTransaction;
	
	public TransactionalStatus(boolean newTransaction) {
		super();
		this.newTransaction = newTransaction;
	}

	public boolean isNewTransaction() {
		return newTransaction;
	}
}