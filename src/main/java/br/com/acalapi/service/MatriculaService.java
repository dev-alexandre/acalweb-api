package br.com.acalapi.service;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private MongoTemplate mongoOperations;

    public List<Matricula> listarMatriculasHidrometro() {
        return mongoOperations.find(new Query().addCriteria(new Criteria().where("possuiHidrometro").is(true)), Matricula.class);
    }

    public List<Matricula> listarPorReferencia(String referencia) {
        Criteria criteria =
            new Criteria()
                .where("hidrometros.referencia").nin(referencia).and("possuiHidrometro").is(true);

        return mongoOperations.find(new Query().addCriteria(criteria), Matricula.class);
    }

    public List<SelectDTO<Matricula>> listarSelect(String name){

        Criteria criteria = new Criteria();
        criteria.orOperator(
            Criteria.where("logradouro.nome").regex(".*"+ name +".*", "i"),
            Criteria.where("logradouro.tipoLogradouro.nome").regex(".*"+ name +".*", "i")
        );

        List<Matricula> matriculas = mongoOperations.find(new Query().limit(5).addCriteria(criteria), Matricula.class);
        List<SelectDTO<Matricula>> dados = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (Matricula m: matriculas) {
            if(!nomes.contains(m.getLogradouro().getTipoLogradouro().getNome())){
                nomes.add(m.getLogradouro().getTipoLogradouro().getNome());

                SelectDTO<Matricula> d = new SelectDTO<>();
                d.setTitle(m.getLogradouro().getTipoLogradouro().getNome());
                dados.add(d);
            }
        }

        for (SelectDTO<Matricula> d: dados) {

            if(d.getValues() == null){
                d.setValues(new ArrayList<>());
            }

            d.getValues().addAll(matriculas.stream().filter(f ->
                    f.getLogradouro().getTipoLogradouro().getNome().equals(d.getTitle())).collect(Collectors.toList()));
        }

        return dados;
    }


}
