package br.gov.pa.prodepa.pae.protocolo.consumer.service;

import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;

public interface ProtocoloService {

	ProtocoloResponseDto protocolarDocumento(ProtocoloDto protocoloDto);

}
