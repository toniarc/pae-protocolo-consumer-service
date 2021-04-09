package br.gov.pa.prodepa.pae.protocolo.consumer.service;

public interface ObjectStorageService {

	void putObject(String uniqueId, byte[] file, String contentType);

}
