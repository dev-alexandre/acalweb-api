package br.com.acalapi.controller.v2;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.filtro.ContratoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.v2.ContratoRepository;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.ContratoService;
import br.com.acalapi.service.v1.DatabaseSequenceService;
import br.com.acalapi.service.v2.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController extends ControllerV2<Contrato, ContratoFiltro> {

    @Autowired
    private ContratoService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

    @RequestMapping(value = "/listar/{referencia}", method = RequestMethod.GET)
    public List<Contrato> listar(@PathVariable String referencia) {
        return service.listarContratosDisponiveisPor(referencia);
    }

    @RequestMapping(value = "/listarMatriculasPorContrato/{referencia}", method = RequestMethod.GET)
    public List<Contrato> listarMatriculasPorContrato(@PathVariable String referencia) {
        return service.listarContratosDisponiveisPor(referencia);
    }

    @RequestMapping(value = "/countByCategoria/{nome}", method = RequestMethod.GET)
    public long countByCategoria(@PathVariable String nome) {
        return service.countByCategoria(nome);
    }


}
