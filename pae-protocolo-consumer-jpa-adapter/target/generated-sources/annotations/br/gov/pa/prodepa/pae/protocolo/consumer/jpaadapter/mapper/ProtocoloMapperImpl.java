package br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.mapper;

import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Anexo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Anexo.AnexoBuilder;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Assinatura;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Assinatura.AssinaturaBuilder;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Interessado;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Interessado.InteressadoBuilder;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.Protocolo.ProtocoloBuilder;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.ProtocoloId;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.ProtocoloId.ProtocoloIdBuilder;
import br.gov.pa.prodepa.pae.protocolo.consumer.domain.model.TipoInteressado;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.AnexoEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.AssinaturaEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.InteressadoEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.ProtocoloEntity;
import br.gov.pa.prodepa.pae.protocolo.consumer.jpaadapter.entity.ProtocoloEntityId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-18T08:21:27-0300",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 3.24.0.v20201123-0742, environment: Java 15.0.1 (Oracle Corporation)"
)
public class ProtocoloMapperImpl implements ProtocoloMapper {

    @Override
    public ProtocoloEntity map(Protocolo model) {
        if ( model == null ) {
            return null;
        }

        ProtocoloEntity protocoloEntity = new ProtocoloEntity();

        protocoloEntity.setId( protocoloIdToProtocoloEntityId( model.getId() ) );
        protocoloEntity.setDataProtocolo( model.getDataProtocolo() );
        protocoloEntity.setEspecieId( model.getEspecieId() );
        protocoloEntity.setAssuntoId( model.getAssuntoId() );
        protocoloEntity.setMunicipioId( model.getMunicipioId() );
        protocoloEntity.setOrigemDocumento( model.getOrigemDocumento() );
        protocoloEntity.setComplemento( model.getComplemento() );
        protocoloEntity.setPrioridade( model.getPrioridade() );
        protocoloEntity.setDocumentoProtocoladoId( model.getDocumentoProtocoladoId() );
        protocoloEntity.setOrgaoDestinoId( model.getOrgaoDestinoId() );
        protocoloEntity.setLocalizacaoDestinoId( model.getLocalizacaoDestinoId() );
        protocoloEntity.setNumeroDocumento( model.getNumeroDocumento() );
        protocoloEntity.setAnoDocumento( model.getAnoDocumento() );
        protocoloEntity.setHashAnexos( model.getHashAnexos() );
        protocoloEntity.setHashAlgoritmo( model.getHashAlgoritmo() );
        protocoloEntity.setUsuarioCadastroId( model.getUsuarioCadastroId() );
        protocoloEntity.setUsuarioCadastroNome( model.getUsuarioCadastroNome() );
        protocoloEntity.setOrgaoOrigemId( model.getOrgaoOrigemId() );
        protocoloEntity.setOrgaoOrigemSigla( model.getOrgaoOrigemSigla() );
        protocoloEntity.setLocalizacaoOrigemId( model.getLocalizacaoOrigemId() );
        protocoloEntity.setInteressados( interessadoSetToInteressadoEntitySet( model.getInteressados() ) );
        protocoloEntity.setAnexos( anexoSetToAnexoEntitySet( model.getAnexos() ) );

        return protocoloEntity;
    }

    @Override
    public Protocolo map(ProtocoloEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProtocoloBuilder protocolo = Protocolo.builder();

        protocolo.anexos( anexoEntitySetToAnexoSet( entity.getAnexos() ) );
        protocolo.anoDocumento( entity.getAnoDocumento() );
        protocolo.assuntoId( entity.getAssuntoId() );
        protocolo.complemento( entity.getComplemento() );
        protocolo.dataProtocolo( entity.getDataProtocolo() );
        protocolo.documentoProtocoladoId( entity.getDocumentoProtocoladoId() );
        protocolo.especieId( entity.getEspecieId() );
        protocolo.hashAlgoritmo( entity.getHashAlgoritmo() );
        protocolo.hashAnexos( entity.getHashAnexos() );
        protocolo.id( protocoloEntityIdToProtocoloId( entity.getId() ) );
        protocolo.interessados( interessadoEntitySetToInteressadoSet( entity.getInteressados() ) );
        protocolo.localizacaoDestinoId( entity.getLocalizacaoDestinoId() );
        protocolo.localizacaoOrigemId( entity.getLocalizacaoOrigemId() );
        protocolo.municipioId( entity.getMunicipioId() );
        protocolo.numeroDocumento( entity.getNumeroDocumento() );
        protocolo.orgaoDestinoId( entity.getOrgaoDestinoId() );
        protocolo.orgaoOrigemId( entity.getOrgaoOrigemId() );
        protocolo.orgaoOrigemSigla( entity.getOrgaoOrigemSigla() );
        protocolo.origemDocumento( entity.getOrigemDocumento() );
        protocolo.prioridade( entity.getPrioridade() );
        protocolo.usuarioCadastroId( entity.getUsuarioCadastroId() );
        protocolo.usuarioCadastroNome( entity.getUsuarioCadastroNome() );

        return protocolo.build();
    }

    protected ProtocoloEntityId protocoloIdToProtocoloEntityId(ProtocoloId protocoloId) {
        if ( protocoloId == null ) {
            return null;
        }

        ProtocoloEntityId protocoloEntityId = new ProtocoloEntityId();

        if ( protocoloId.getAno() != null ) {
            protocoloEntityId.setAno( protocoloId.getAno().shortValue() );
        }
        if ( protocoloId.getNumeroProtocolo() != null ) {
            protocoloEntityId.setNumeroProtocolo( protocoloId.getNumeroProtocolo() );
        }

        return protocoloEntityId;
    }

    protected InteressadoEntity interessadoToInteressadoEntity(Interessado interessado) {
        if ( interessado == null ) {
            return null;
        }

        InteressadoEntity interessadoEntity = new InteressadoEntity();

        interessadoEntity.setId( interessado.getId() );
        interessadoEntity.setProtocolo( map( interessado.getProtocolo() ) );
        interessadoEntity.setPessoaId( interessado.getPessoaId() );
        interessadoEntity.setNome( interessado.getNome() );
        if ( interessado.getTipoInteressado() != null ) {
            interessadoEntity.setTipoInteressado( interessado.getTipoInteressado().name() );
        }
        interessadoEntity.setSigla( interessado.getSigla() );
        interessadoEntity.setSetorId( interessado.getSetorId() );

        return interessadoEntity;
    }

    protected Set<InteressadoEntity> interessadoSetToInteressadoEntitySet(Set<Interessado> set) {
        if ( set == null ) {
            return null;
        }

        Set<InteressadoEntity> set1 = new HashSet<InteressadoEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Interessado interessado : set ) {
            set1.add( interessadoToInteressadoEntity( interessado ) );
        }

        return set1;
    }

    protected AssinaturaEntity assinaturaToAssinaturaEntity(Assinatura assinatura) {
        if ( assinatura == null ) {
            return null;
        }

        AssinaturaEntity assinaturaEntity = new AssinaturaEntity();

        assinaturaEntity.setId( assinatura.getId() );
        assinaturaEntity.setAnexo( anexoToAnexoEntity( assinatura.getAnexo() ) );
        assinaturaEntity.setUsuarioAssinanteId( assinatura.getUsuarioAssinanteId() );
        assinaturaEntity.setTipoAssinatura( assinatura.getTipoAssinatura() );
        assinaturaEntity.setDataRequisicao( assinatura.getDataRequisicao() );
        assinaturaEntity.setUsuarioRequisitanteId( assinatura.getUsuarioRequisitanteId() );
        byte[] dadosAssinatura = assinatura.getDadosAssinatura();
        if ( dadosAssinatura != null ) {
            assinaturaEntity.setDadosAssinatura( Arrays.copyOf( dadosAssinatura, dadosAssinatura.length ) );
        }
        assinaturaEntity.setAssinado( assinatura.getAssinado() );

        return assinaturaEntity;
    }

    protected Set<AssinaturaEntity> assinaturaSetToAssinaturaEntitySet(Set<Assinatura> set) {
        if ( set == null ) {
            return null;
        }

        Set<AssinaturaEntity> set1 = new HashSet<AssinaturaEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Assinatura assinatura : set ) {
            set1.add( assinaturaToAssinaturaEntity( assinatura ) );
        }

        return set1;
    }

    protected AnexoEntity anexoToAnexoEntity(Anexo anexo) {
        if ( anexo == null ) {
            return null;
        }

        AnexoEntity anexoEntity = new AnexoEntity();

        anexoEntity.setId( anexo.getId() );
        anexoEntity.setProtocolo( map( anexo.getProtocolo() ) );
        anexoEntity.setAssinadoPorTodos( anexo.getAssinadoPorTodos() );
        anexoEntity.setQuantidadeAssinaturas( anexo.getQuantidadeAssinaturas() );
        anexoEntity.setConteudo( anexo.getConteudo() );
        anexoEntity.setAnexadoEm( anexo.getAnexadoEm() );
        anexoEntity.setS3StorageId( anexo.getS3StorageId() );
        anexoEntity.setTamanhoArquivoMb( anexo.getTamanhoArquivoMb() );
        anexoEntity.setQuantidadePaginas( anexo.getQuantidadePaginas() );
        anexoEntity.setDocumentoInicial( anexo.getDocumentoInicial() );
        anexoEntity.setSequencial( anexo.getSequencial() );
        anexoEntity.setHashArquivo( anexo.getHashArquivo() );
        anexoEntity.setHashAlgoritmo( anexo.getHashAlgoritmo() );
        anexoEntity.setAssinaturas( assinaturaSetToAssinaturaEntitySet( anexo.getAssinaturas() ) );

        return anexoEntity;
    }

    protected Set<AnexoEntity> anexoSetToAnexoEntitySet(Set<Anexo> set) {
        if ( set == null ) {
            return null;
        }

        Set<AnexoEntity> set1 = new HashSet<AnexoEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Anexo anexo : set ) {
            set1.add( anexoToAnexoEntity( anexo ) );
        }

        return set1;
    }

    protected Assinatura assinaturaEntityToAssinatura(AssinaturaEntity assinaturaEntity) {
        if ( assinaturaEntity == null ) {
            return null;
        }

        AssinaturaBuilder assinatura = Assinatura.builder();

        assinatura.anexo( anexoEntityToAnexo( assinaturaEntity.getAnexo() ) );
        assinatura.assinado( assinaturaEntity.getAssinado() );
        byte[] dadosAssinatura = assinaturaEntity.getDadosAssinatura();
        if ( dadosAssinatura != null ) {
            assinatura.dadosAssinatura( Arrays.copyOf( dadosAssinatura, dadosAssinatura.length ) );
        }
        assinatura.dataRequisicao( assinaturaEntity.getDataRequisicao() );
        assinatura.id( assinaturaEntity.getId() );
        assinatura.tipoAssinatura( assinaturaEntity.getTipoAssinatura() );
        assinatura.usuarioAssinanteId( assinaturaEntity.getUsuarioAssinanteId() );
        assinatura.usuarioRequisitanteId( assinaturaEntity.getUsuarioRequisitanteId() );

        return assinatura.build();
    }

    protected Set<Assinatura> assinaturaEntitySetToAssinaturaSet(Set<AssinaturaEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Assinatura> set1 = new HashSet<Assinatura>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AssinaturaEntity assinaturaEntity : set ) {
            set1.add( assinaturaEntityToAssinatura( assinaturaEntity ) );
        }

        return set1;
    }

    protected Anexo anexoEntityToAnexo(AnexoEntity anexoEntity) {
        if ( anexoEntity == null ) {
            return null;
        }

        AnexoBuilder anexo = Anexo.builder();

        anexo.anexadoEm( anexoEntity.getAnexadoEm() );
        anexo.assinadoPorTodos( anexoEntity.isAssinadoPorTodos() );
        anexo.assinaturas( assinaturaEntitySetToAssinaturaSet( anexoEntity.getAssinaturas() ) );
        anexo.conteudo( anexoEntity.getConteudo() );
        anexo.documentoInicial( anexoEntity.isDocumentoInicial() );
        anexo.hashAlgoritmo( anexoEntity.getHashAlgoritmo() );
        anexo.hashArquivo( anexoEntity.getHashArquivo() );
        anexo.id( anexoEntity.getId() );
        anexo.protocolo( map( anexoEntity.getProtocolo() ) );
        anexo.quantidadeAssinaturas( anexoEntity.getQuantidadeAssinaturas() );
        anexo.quantidadePaginas( anexoEntity.getQuantidadePaginas() );
        anexo.s3StorageId( anexoEntity.getS3StorageId() );
        anexo.sequencial( anexoEntity.getSequencial() );
        anexo.tamanhoArquivoMb( anexoEntity.getTamanhoArquivoMb() );

        return anexo.build();
    }

    protected Set<Anexo> anexoEntitySetToAnexoSet(Set<AnexoEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Anexo> set1 = new HashSet<Anexo>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AnexoEntity anexoEntity : set ) {
            set1.add( anexoEntityToAnexo( anexoEntity ) );
        }

        return set1;
    }

    protected ProtocoloId protocoloEntityIdToProtocoloId(ProtocoloEntityId protocoloEntityId) {
        if ( protocoloEntityId == null ) {
            return null;
        }

        ProtocoloIdBuilder protocoloId = ProtocoloId.builder();

        protocoloId.ano( (int) protocoloEntityId.getAno() );
        protocoloId.numeroProtocolo( protocoloEntityId.getNumeroProtocolo() );

        return protocoloId.build();
    }

    protected Interessado interessadoEntityToInteressado(InteressadoEntity interessadoEntity) {
        if ( interessadoEntity == null ) {
            return null;
        }

        InteressadoBuilder interessado = Interessado.builder();

        interessado.id( interessadoEntity.getId() );
        interessado.nome( interessadoEntity.getNome() );
        interessado.pessoaId( interessadoEntity.getPessoaId() );
        interessado.protocolo( map( interessadoEntity.getProtocolo() ) );
        interessado.setorId( interessadoEntity.getSetorId() );
        interessado.sigla( interessadoEntity.getSigla() );
        if ( interessadoEntity.getTipoInteressado() != null ) {
            interessado.tipoInteressado( Enum.valueOf( TipoInteressado.class, interessadoEntity.getTipoInteressado() ) );
        }

        return interessado.build();
    }

    protected Set<Interessado> interessadoEntitySetToInteressadoSet(Set<InteressadoEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Interessado> set1 = new HashSet<Interessado>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( InteressadoEntity interessadoEntity : set ) {
            set1.add( interessadoEntityToInteressado( interessadoEntity ) );
        }

        return set1;
    }
}
