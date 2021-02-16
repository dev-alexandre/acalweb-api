package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.GrupoRepository;
import br.com.acalapi.service.v1.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends Controller<Grupo, Filtro> {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private GrupoService service;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @RequestMapping(value = "/selecionar", method = RequestMethod.GET)
    public List<SelectDTO<Grupo>> Selecionar() {
        return service.listarSelect();
    }

    @Override
    public Sort getSort() {
        return Sort.by("categoria.nome", "nome");
    }

    @Override
    public Query getQueryDuplicidade(Grupo grupo) {
        return new Query().limit(1).addCriteria(
            Criteria
                .where("nome").regex("^" + grupo.getNome().trim() + "$", "i")
                .and("categoria").is(grupo.getCategoria())
        );
    }

}
