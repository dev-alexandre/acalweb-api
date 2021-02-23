package br.com.acalapi.controller.v2;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.Analise;
import br.com.acalapi.filtro.AnaliseFiltro;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.AnaliseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/analise")
public class AnaliseController extends ControllerV2<Analise, AnaliseFiltro> {

    @Autowired
    private AnaliseService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

    @RequestMapping(value = "/buscarPorReferencia/{referencia}", method = RequestMethod.GET)
    public Analise buscarPorReferencia(@PathVariable String referencia) {
        return service.findByRerencia(referencia);
    }

}
