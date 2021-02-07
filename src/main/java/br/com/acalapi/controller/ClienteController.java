package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.Cliente;
import br.com.acalapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends Controller<Cliente, Filtro>{

    @Autowired
    private ClienteRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Sort getSort() {
        return Sort.by("nome");
    }

    @Override
    public Query getQueryDuplicidade(Cliente cliente) {
        return
            new Query().addCriteria(
                Criteria.where("documento").is(cliente.getDocumento())
        );
    }

    @RequestMapping(value="/listar/{nome}", method = RequestMethod.GET)
    public List<Cliente> listarPorNome(@PathVariable String nome){
        return repository.findByname(nome);
    }

    @Override
    public void editar(@RequestBody Cliente cliente) {
        repository.save(cliente);
    }


}
