package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.repository.MatriculaRepository;
import br.com.acalapi.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matricula")
public class MatriculaController extends Controller<Matricula, Filtro>{

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private MatriculaService service;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    public Sort getSort() {
        return
            Sort.by("logradouro.tipoLogradouro.nome").and(Sort.by("logradouro.nome").and(Sort.by("numero").and(Sort.by("letra"))));
    }

    @RequestMapping(value="/selecionar/{nome}", method = RequestMethod.GET)
    public List<SelectDTO<Matricula>> Selecionar(@PathVariable String nome) {
        return service.listarSelect(nome);
    }

}
