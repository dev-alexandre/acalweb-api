package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.repository.GrupoRepository;
import br.com.acalapi.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends Controller<Grupo, Filtro>{


    @Autowired
    private GrupoRepository repository;

    @Autowired
    private GrupoService service;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @RequestMapping(value="/selecionar", method = RequestMethod.GET)
    public List<SelectDTO<Grupo>> Selecionar() {
        return service.listarSelect();
    }

}
