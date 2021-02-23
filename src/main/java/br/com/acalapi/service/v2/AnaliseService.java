package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Analise;
import br.com.acalapi.entity.Cliente;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.AnaliseFiltro;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.repository.v2.AnaliseRepository;
import br.com.acalapi.repository.v2.ClienteRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnaliseService extends ServiceV2<Analise, AnaliseFiltro> {

    @Autowired
    private AnaliseRepository repository;

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }


    @Override
    public Query getQuery(AnaliseFiltro filtro, Query query) {
        filterReferencia(filtro, query);
        filterParametro(filtro, query);
        filterExigido(filtro, query);
        filterAnalisado(filtro, query);
        filterConforme(filtro, query);
        return query;
    }

    private void filterReferencia(AnaliseFiltro filtro, Query query) {
        if(super.isValid(filtro.getReferencia()) ){
            query.addCriteria(
                Criteria.where("referencia").is(filtro.getReferencia().getValor())
            );
        }
    }

    private void filterParametro(AnaliseFiltro filtro, Query query) {
        if(super.isValid(filtro.getParametro())){
            query.addCriteria(
                Criteria.where("coletas.parametro").is(filtro.getParametro().getValor())
            );
        }
    }

    private void filterExigido(AnaliseFiltro filtro, Query query) {
        if(super.isValid(filtro.getExigido())){
            query.addCriteria(
                Criteria.where("coletas.exigido").is(filtro.getExigido().getValor())
            );
        }
    }

    private void filterAnalisado(AnaliseFiltro filtro, Query query) {
        if(super.isValid(filtro.getAnalisado())){
            query.addCriteria(
                Criteria.where("coletas.analisado").is(filtro.getAnalisado().getValor())
            );
        }
    }

    private void filterConforme(AnaliseFiltro filtro, Query query) {
        if(super.isValid(filtro.getConforme())){
            query.addCriteria(
                Criteria.where("coletas.conforme").is(filtro.getConforme().getValor())
            );
        }
    }

    public Analise findByRerencia(String referencia){
        return repository.findByReferencia(referencia);
    }

}
