package br.com.acalapi.controller.v2;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.controller.v2.ControllerV2;
import br.com.acalapi.entity.Hidrometro;
import br.com.acalapi.filtro.HidrometroFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.v2.HidrometroRepository;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.HidrometroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hidrometro")
public class HidrometroController extends ControllerV2<Hidrometro, HidrometroFiltro> {

    @Autowired
    public HidrometroService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

}
