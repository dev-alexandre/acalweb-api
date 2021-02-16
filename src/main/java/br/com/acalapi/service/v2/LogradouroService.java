package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.filtro.LogradouroFiltro;
import br.com.acalapi.repository.v2.LogradouroRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class LogradouroService extends ServiceV2<Logradouro, LogradouroFiltro> {

    @Autowired
    private LogradouroRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQuery(LogradouroFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterTipoLogradouro(filtro, query);
        return query;
    }

    private void filterNome(LogradouroFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null){
            query.addCriteria(
                Criteria.where("nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }

    private void filterTipoLogradouro(LogradouroFiltro filtro, Query query) {
        if (filtro.getTipoLogradouro() != null && filtro.getTipoLogradouro().getValor() != null) {
            query.addCriteria(
                Criteria.where("tipoLogradouro.nome").regex(".*" + filtro.getTipoLogradouro().getValor() + ".*", "i")
            );
        }
    }

}
