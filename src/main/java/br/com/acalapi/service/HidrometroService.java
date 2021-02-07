package br.com.acalapi.service;

import br.com.acalapi.entity.Hidrometro;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HidrometroService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MatriculaRepository repository;


    public List<Hidrometro> listarContratosDisponiveisPor(String referencia) {
        List<Hidrometro> hidrometros = new ArrayList<>();

        Query q = new Query();
        q.addCriteria(Criteria.where("hidrometro").nin(null, ""));

        List<Matricula> matriculas = mongoTemplate.find(q, Matricula.class);

        matriculas.forEach(c->{
            Hidrometro h = new Hidrometro();
            h.setReferencia(referencia);
            //h.setMatricula(c);
            hidrometros.add(h);
        });


        return hidrometros;
    }

}
