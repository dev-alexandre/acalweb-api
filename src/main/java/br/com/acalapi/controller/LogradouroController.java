package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.repository.LogradouroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logradouro")
public class LogradouroController extends Controller<Logradouro, Filtro>{

    @Autowired
    private LogradouroRepository service;

    @RequestMapping(value="/listar", method = RequestMethod.GET)
    public List<Logradouro> listar(){
        return
            service.findAll(
                Sort.by("tipoLogradouro.nome").ascending().and(
                Sort.by("nome").ascending())
            );
    }

    @RequestMapping(value="/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody Logradouro logradouro){
        service.save(logradouro);
    }

    @RequestMapping(value="/editar", method = RequestMethod.PUT)
    public void editar(@RequestBody Logradouro logradouro){
        service.save(logradouro);
    }

    @RequestMapping(value="/deletar/{id}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable String id) {
        service.deleteById(id);
    }

}
