package br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.dto.ProtocoloResponseDto;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.adapter.repository.SequencialProtocoloJpaRepository;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.AnexoEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.AssinaturaEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.InteressadoEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.ProtocoloEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.mapper.ProtocoloMapper;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.repository.ProtocoloJpaRepository;
import br.gov.pa.prodepa.pae.protocolo.consumer.port.ProtocoloRepository;

@Component
public class ProtocoloPersistenceAdapter implements ProtocoloRepository {

	@Autowired
	private ProtocoloJpaRepository repository;
	
	@Autowired
	private SequencialProtocoloJpaRepository sequencialProtocoloRepository;
	
	@Override
	public ProtocoloResponseDto salvar(Protocolo protocolo) {
		
		ProtocoloEntity entity = ProtocoloMapper.INSTANCE.map(protocolo);

		repository.getOne(entity.getId());
		
		if(entity.getInteressados() != null) {
			for(InteressadoEntity i : entity.getInteressados()) {
				i.setProtocolo(entity);
			}
		}
		
		if(entity.getAnexos() != null) {
			for(AnexoEntity an : entity.getAnexos()) {
				an.setProtocolo(entity);
				
				if(an.getAssinaturas() != null) {
					for(AssinaturaEntity a : an.getAssinaturas()) {
						a.setAnexo(an);
					}
				}
			}
		}
		
		repository.saveAndFlush(entity);
		return ProtocoloResponseDto.builder().ano(protocolo.getId().getAno()).numero(protocolo.getId().getNumeroProtocolo()).build();
	}

	@Override
	public Long buscarProximoSequencial(Integer ano) {
		Long sequencial = sequencialProtocoloRepository.buscarProximoSequencial(ano);
		
		if (sequencial == null) {
			return null;
		}
		
		sequencial++;
		sequencialProtocoloRepository.incrementarSequencial(ano, sequencial);
		return sequencial;
	}

	@Override
	public void criarSequencia(int ano, Long sequencial) {
		sequencialProtocoloRepository.insert(ano, sequencial);
	}

}
