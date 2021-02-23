package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.entity.Hidrometro;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.filtro.HidrometroFiltro;
import br.com.acalapi.repository.v2.ClienteRepository;
import br.com.acalapi.repository.v2.HidrometroRepository;
import br.com.acalapi.repository.v2.MatriculaRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HidrometroService extends ServiceV2<Hidrometro, HidrometroFiltro> {

    @Autowired
    private HidrometroRepository repository;


    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Query getQuery(HidrometroFiltro filtro, Query query) {
        return query;
    }

    @Override
    public PagingAndSortingRepository<Hidrometro, String> getRepository() {
        return repository;
    }

    public List<Hidrometro> listarContratosDisponiveisPor(String referencia) {
        List<Hidrometro> hidrometros = new ArrayList<>();

        Query q = new Query();
        q.addCriteria(Criteria.where("hidrometro").nin(null, ""));

        List<Matricula> matriculas = mongoTemplate.find(q, Matricula.class);

        matriculas.forEach(c -> {
            Hidrometro h = new Hidrometro();
            h.setReferencia(referencia);
            hidrometros.add(h);
        });


        return hidrometros;
    }


}
