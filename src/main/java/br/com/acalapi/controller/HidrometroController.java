package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.Hidrometro;
import br.com.acalapi.entity.financeiro.Boleto;
import br.com.acalapi.repository.HidrometroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hidrometro")
public class HidrometroController extends Controller<Hidrometro, Filtro>{

    @Autowired
    private HidrometroRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQueryDuplicidade(Hidrometro hidrometro) {
        return null;
    }

}
