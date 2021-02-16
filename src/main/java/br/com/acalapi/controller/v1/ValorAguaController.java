package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.ValorAgua;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.ValorAguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/valor-agua")
public class ValorAguaController extends Controller<ValorAgua, Filtro> {

    @Autowired
    private ValorAguaRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }


    @Override
    public Query getQueryDuplicidade(ValorAgua valorAgua) {
        return null;
    }

    @RequestMapping(value = "/buscar/atual", method = RequestMethod.GET)
    public ValorAgua buscarAtual() {
        return repository.findTopByOrderByDataDesc();
    }

    @Override
    public void salvar(@RequestBody ValorAgua valorAgua) {
        valorAgua.setData(LocalDateTime.now());
        super.salvar(valorAgua);
    }


}
