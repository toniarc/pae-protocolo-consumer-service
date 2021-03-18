package br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.ProtocoloEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.ProtocoloEntityId;

@Repository
public interface ProtocoloJpaRepository extends JpaRepository<ProtocoloEntity, ProtocoloEntityId> {

}
