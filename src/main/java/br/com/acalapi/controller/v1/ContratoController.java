package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.Contrato;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.ContratoRepository;
import br.com.acalapi.service.v1.ContratoService;
import br.com.acalapi.service.v1.DatabaseSequenceService;
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
public class ContratoController extends Controller<Contrato, Filtro> {

    @Autowired
    private ContratoService service;

    @Autowired
    private ContratoRepository repository;

    @Autowired
    private DatabaseSequenceService databaseSequenceService;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }


    @Override
    public void salvar(Contrato contrato) {
        if (contrato.getNumero() == null) {
            contrato.setNumero(databaseSequenceService.generateSequence(Contrato.SEQUENCE_NAME));
        }
        super.salvar(contrato);
    }

    @Override
    public Sort getSort() {
        return Sort.by("cliente.nome", "matricula.logradouro.tipoLogradouro.nome", "matricula.logradouro.nome");
    }

    @Override
    public Query getQueryDuplicidade(Contrato contrato) {
        return new Query().addCriteria(
            Criteria
                .where("matricula.numero").is(contrato.getMatricula().getNumero())
                    .and("matricula.letra").is(contrato.getMatricula().getLetra())
                    .and("matricula.logradouro").is(contrato.getMatricula().getLogradouro())
                    .and("habilitado").is(true)
                    .and("ativo").is(true)
        );
    }

    @RequestMapping(value = "/listar/{referencia}", method = RequestMethod.GET)
    public List<Contrato> listar(@PathVariable String referencia) {
        return service.listarContratosDisponiveisPor(referencia);
    }

    @RequestMapping(value = "/countByCategoria/{nome}", method = RequestMethod.GET)
    public long countByCategoria(@PathVariable String nome) {
        return repository.countByCategoria(nome);
    }
}
