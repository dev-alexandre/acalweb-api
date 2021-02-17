package br.com.acalapi.controller.v2;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.filtro.GrupoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.v2.GrupoRepository;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.GrupoService;
import br.com.acalapi.service.v2.LogradouroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController extends ControllerV2<Grupo, GrupoFiltro> {

    @Autowired
    private GrupoService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }


    @RequestMapping(value = "/selecionar", method = RequestMethod.GET)
    public List<SelectDTO<Grupo>> Selecionar() {
        return service.listarSelect();
    }

}
