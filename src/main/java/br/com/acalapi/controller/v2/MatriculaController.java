package br.com.acalapi.controller.v2;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.filtro.MatriculaFiltro;
import br.com.acalapi.service.ServiceV2;
import br.com.acalapi.service.v2.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/matricula")
public class MatriculaController extends ControllerV2<Matricula, MatriculaFiltro> {

    @Autowired
    private MatriculaService service;

    @Override
    public ServiceV2 getService() {
        return service;
    }

    @RequestMapping(value = "/selecionar/{matricula}", method = RequestMethod.GET)
    public  List<SelectDTO<Matricula>> selecionar(@PathVariable String matricula) {
        return service.selecionar(matricula);
    }

}
