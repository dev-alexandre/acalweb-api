package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.repository.LogradouroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RestController
@RequestMapping("/logradouro")
public class LogradouroController extends Controller<Logradouro, Filtro>{

    @Autowired
    private LogradouroRepository service;

    public MongoRepository getRepository() {
        return service;
    }

    @Override
    public Sort getSort() {
        return
            Sort
                .by("tipoLogradouro.nome").ascending()
                .and(Sort.by("nome").ascending());
    }

    @Override
    public Query getQueryName(Pageable pageable, Filtro filtro){
        Aggregation agg = (Aggregation) Aggregation.newAggregation(
            project("nome").and("tipoLogradouro.nome").previousOperation()
        );

        if(filtro.getName() == null){
            return new Query().with(pageable);
        }

        return
            new Query().with(pageable).addCriteria(
                new Criteria().orOperator(
                    Criteria.where("nome").regex( filtro.getName().trim(),  "i"),
                    Criteria.where("tipoLogradouro.nome").regex( filtro.getName().trim(),  "i")
                )
            );
    }

    public Query getQueryDuplicidade(Logradouro logradouro){
       return
           new Query().addCriteria(
               Criteria
                   .where("nome").regex("^" + logradouro.getNome().trim() + "$",  "i")
                   .and("tipoLogradouro").is(logradouro.getTipoLogradouro())
       );

    }

}
