package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.enumeration.constante.CheckboxEnum;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.filtro.ContratoFiltro;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.repository.v2.ContratoRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContratoService extends ServiceV2<Contrato, ContratoFiltro> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ContratoRepository repository;

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQuery(ContratoFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterTipoLogradouroNome(filtro, query);
        filterLogradouroNome(filtro, query);
        filterNumero(filtro, query);
        filterLetra(filtro, query);
        filterGrupo(filtro, query);
        filterCategoria(filtro, query);
        filterPrincipal(filtro, query);

        return query;
    }


    private void filterNome(ContratoFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null && !filtro.getNome().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("cliente.nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }

    private void filterTipoLogradouroNome(ContratoFiltro filtro, Query query) {
        if(filtro.getTipoLogradouro() != null && filtro.getTipoLogradouro().getValor() != null && !filtro.getTipoLogradouro().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("matricula.logradouro.tipoLogradouro.nome").regex(".*" + filtro.getTipoLogradouro().getValor() + ".*", "i")
            );
        }
    }

    private void filterLogradouroNome(ContratoFiltro filtro, Query query) {
        if(filtro.getLogradouro() != null && filtro.getLogradouro().getValor() != null && !filtro.getLogradouro().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("matricula.logradouro.nome").regex(".*" + filtro.getLogradouro().getValor() + ".*", "i")
            );
        }
    }

    private void filterNumero(ContratoFiltro filtro, Query query) {
        if(filtro.getNumero() != null && filtro.getNumero().getValor() != null && !filtro.getNumero().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("matricula.numero").is(Long.valueOf( filtro.getNumero().getValor()))
            );
        }
    }

    private void filterLetra(ContratoFiltro filtro, Query query) {
        if(filtro.getLetra() != null && filtro.getLetra().getValor() != null && !filtro.getLetra().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("matricula.letra").regex(".*" + filtro.getLetra().getValor() + ".*", "i")
            );
        }
    }

    private void filterGrupo(ContratoFiltro filtro, Query query) {
        if(filtro.getGrupo() != null && filtro.getGrupo().getValor() != null && !filtro.getGrupo().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("grupo.nome").regex(".*" + filtro.getGrupo().getValor() + ".*", "i")
            );
        }
    }

    private void filterCategoria(ContratoFiltro filtro, Query query) {
        if(filtro.getCategoria() != null && filtro.getCategoria().getValor() != null && !filtro.getCategoria().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("grupo.categoria.nome").regex(".*" + filtro.getCategoria().getValor() + ".*", "i")
            );
        }
    }


    private void filterPrincipal(ContratoFiltro filtro, Query query) {
        if(filtro.getPrincipal() != null && filtro.getPrincipal().getValor() != null && !filtro.getPrincipal().getValor().isEmpty()){

           if(filtro.getPrincipal().getValor().equals(CheckboxEnum.SIM.getValor())){
               query.addCriteria(
                   Criteria.where("contratoPrincipal").is(true)
               );
           } else  if(filtro.getPrincipal().getValor().equals(CheckboxEnum.NAO.getValor())){
               query.addCriteria(
                   Criteria.where("contratoPrincipal").is(false)
               );
           }

        }
    }


    @Transactional
    @Override
    public void salvar(Contrato contrato) {
        verificarSalvar(contrato);
        permitirSomenteUmaMatriculaAtiva(contrato);
        getRepository().save(contrato);
    }

    @Override
    public void verificarSalvar(Contrato contrato) {
        //A Matricula está diponivel?
        Query queryMatricula = new Query();
        queryMatricula.addCriteria(
            Criteria
                .where("matricula.id").is(contrato.getMatricula().getId())
                .and("ativo").is(true)
        );

        if(mongoOperations.exists(queryMatricula, Contrato.class)){
            throw new ConflictDataException("Essa matricula já esta vinculada a um contrato", HttpStatus.CONFLICT);
        }

    }






    public List<Contrato> listarContratosDisponiveisPor(String referencia) {

        Query q = new Query();
        q.addCriteria(
                Criteria.where("referencias").nin(referencia).and("habilitado").is(true)
        );

        return mongoTemplate.find(q, Contrato.class);
    }

    public long countByCategoria(String nome){
        return repository.countByCategoria(nome);
    }


    private void permitirSomenteUmaMatriculaAtiva(Contrato contrato){

        if(contrato.getContratoPrincipal()){

            Query q = new Query();
            q.addCriteria(
                    Criteria.where("cliente.documento").is(contrato.getCliente().getDocumento())
                            .and("contratoPrincipal").is(true)
            );

            List<Contrato> contratoPrincipais = mongoTemplate.find(q, Contrato.class);
            contratoPrincipais.forEach(c ->{
                c.setContratoPrincipal(false);
                    getRepository().save(c);
            });
        }


    }
}
