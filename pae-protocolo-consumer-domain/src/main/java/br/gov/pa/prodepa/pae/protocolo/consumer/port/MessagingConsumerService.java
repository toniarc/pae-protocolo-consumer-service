package br.gov.pa.prodepa.pae.protocolo.consumer.port;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;

public interface MessagingConsumerService {

	void enviarParaFilaGeracaoPdf(ProtocoloDto protocoloDto);

	void enviarNotificacao(ProtocoloResponseDto protocoloSalvo);

}
