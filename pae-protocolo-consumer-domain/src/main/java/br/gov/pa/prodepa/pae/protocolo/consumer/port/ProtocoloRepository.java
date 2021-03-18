package br.gov.pa.prodepa.pae.protocolo.consumer.port;

import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;

public interface ProtocoloRepository {

	ProtocoloResponseDto salvar(Protocolo protocolo);

	Long buscarProximoSequencial(Integer ano);

	void criarSequencia(int ano, Long sequencial);

}
