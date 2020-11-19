package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.entity.Referencia;
import br.com.acalapi.repository.ContratoRepository;
import br.com.acalapi.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController extends Controller<Contrato, Filtro>{

    @Autowired
    private ContratoRepository repository;

    @Autowired
    private ContratoService service;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Sort getSort() {
        return Sort.by("tipoLogradouro.nome").ascending().and(Sort.by("nome").ascending());
    }


    @RequestMapping(value="/listar/{mes}/{ano}", method = RequestMethod.GET)
    public List<Contrato> listar(@PathVariable int mes, @PathVariable int ano){
        return service.listarContratosDisponiveisPor(new Referencia(mes, ano));
    }

}
