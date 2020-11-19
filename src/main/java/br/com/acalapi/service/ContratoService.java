package br.com.acalapi.service;

import br.com.acalapi.entity.Contrato;
import br.com.acalapi.entity.Referencia;
import br.com.acalapi.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ContratoRepository repository;

    public List<Contrato> listarContratosDisponiveisPor(Referencia referencia) {

        Query q = new Query();
        q.addCriteria(
            Criteria.where("habilitado").ne(false).and("refecrencias").nin(referencia)
        );

        return mongoTemplate.find(q, Contrato.class);
    }

}
