package br.com.acalapi.controller.v2;

import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logradouro")
public class LogradouroController extends ControllerV2<Logradouro, Filtro> {

    @Autowired
    private ClienteService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

}
