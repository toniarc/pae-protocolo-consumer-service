package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.documento.ModeloEstruturaBasicDto;

public interface Html2PdfService {

	byte[] gerarPdf(String conteudo, ModeloEstruturaBasicDto me);
}
