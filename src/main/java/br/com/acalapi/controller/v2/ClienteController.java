package br.com.acalapi.controller.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends ControllerV2<Cliente, ClienteFiltro> {

    @Autowired
    private ClienteService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

    @Autowired
    protected MongoTemplate mongoOperations;


    @RequestMapping(value = "/teste/order", method = RequestMethod.GET)
    public Page<Cliente> teste() {
        Filtro filtro = new Filtro();

        Sort sort = Sort.by(Sort.Direction.ASC, "documento");


        Pageable pageable = PageRequest.of(0, 10, sort);

        Query query = new Query();
        return paginar(pageable, query);
    }

    private Page<Cliente> paginar(Pageable pageable, Query query){
        return
            PageableExecutionUtils.getPage(
                mongoOperations.find(query, Cliente.class),
                pageable,
                () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), Cliente.class));
    }

}
