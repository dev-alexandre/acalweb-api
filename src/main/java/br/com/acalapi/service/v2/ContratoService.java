package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.enumeration.constante.CheckboxEnum;
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
import org.springframework.stereotype.Service;

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
        //filterMatricula(filtro, query);
        filterPrincipal(filtro, query);
        //filterValor(filtro, query);
        return query;
    }


    private void filterNome(ContratoFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null && !filtro.getNome().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("cliente.nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }
    private void filterPrincipal(ContratoFiltro filtro, Query query) {
        if(filtro.getPrincipal() != null && filtro.getPrincipal().getValor() != null && !filtro.getPrincipal().getValor().isEmpty()){

            if(filtro.getPrincipal().getValor().equals(CheckboxEnum.SIM.getValor())){
                query.addCriteria(
                    Criteria.where("contratoPrincipal").is(true)
                );
            } else if (filtro.getPrincipal().getValor().equals(CheckboxEnum.NAO.getValor())){
                query.addCriteria(
                    Criteria.where("contratoPrincipal").is(false)
                );
            }
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

}
