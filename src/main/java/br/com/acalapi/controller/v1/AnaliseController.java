package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.Analise;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.AnaliseRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/analise")
public class AnaliseController extends Controller<Analise, Filtro> {

    @Autowired
    private AnaliseRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Sort getSort() {
        return
                Sort.by("referenciaInicial", "referenciaFim");
    }

    @Override
    public void editar(@RequestBody Analise analise) {
        repository.save(analise);
    }


    @RequestMapping(value = "/buscarPorReferencia/{referencia}", method = RequestMethod.GET)
    public Analise buscarPorReferencia(@PathVariable String referencia) {
        return
                repository.findByReferencia(referencia);
    }

    @Override
    public Query getQueryDuplicidade(Analise analise) {
        return new Query().addCriteria(
                Criteria
                        .where("referencia").is(analise.getReferencia())
        );
    }

}
