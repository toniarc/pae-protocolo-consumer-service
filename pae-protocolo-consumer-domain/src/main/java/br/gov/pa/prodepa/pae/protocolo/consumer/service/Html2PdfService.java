package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import br.gov.pa.prodepa.pae.documento.client.ModeloEstruturaBasicDto;

public interface Html2PdfService {

	byte[] gerarPdf(String conteudo, ModeloEstruturaBasicDto me);
}
