package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.repository.HidrometroRepository;
import br.com.acalapi.repository.MatriculaRepository;
import br.com.acalapi.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/matricula")
public class MatriculaController extends Controller<Matricula, Filtro>{

    @Autowired
    private MatriculaService service;

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private HidrometroRepository hidrometroRepository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Sort getSort() {
        return
            Sort.by("logradouro.tipoLogradouro.nome","logradouro.nome","numero", "letra");
    }

    @Override
    @RequestMapping(value="/salvar-todos", method = RequestMethod.POST)
    public void salvar(@RequestBody List<Matricula> matricula) {
        matricula.forEach(m -> {
            verificarDuplicidade(m);
        });

        matricula.forEach(m-> {
            hidrometroRepository.saveAll(m.getHidrometroList());
        });

        getRepository().saveAll(matricula);
    }

    @RequestMapping(value="/selecionar/{nome}", method = RequestMethod.GET)
    public List<SelectDTO<Matricula>> Selecionar(@PathVariable String nome) {
        return service.listarSelect(nome);
    }

    @RequestMapping(value="/listarPorReferencia/{referencia}", method = RequestMethod.GET)
    public List<Matricula> listarPorReferencia(@PathVariable String referencia){
        return service.listarPorReferencia(referencia);
    }

    @RequestMapping(value="/listar/hidrometro", method = RequestMethod.GET)
    public List<Matricula> listarMatriculasHidrometro(){
        return service.listarMatriculasHidrometro();
    }

    @Override
    public Query getQueryName(Pageable pageable, Filtro filtro){
        if(filtro.getName() == null){
            return new Query().with(pageable);
        }

        return
            new Query().with(pageable).addCriteria(
                new Criteria().orOperator(
                    Criteria.where("logradouro.nome").regex( filtro.getName().trim(),  "i"),
                    Criteria.where("logradouro.tipoLogradouro.nome").regex( filtro.getName().trim(),  "i")
                )
            );

    }

    @Override
    public Query getQueryDuplicidade(Matricula novo) {
        Criteria criteria;

        if (novo.getLetra() == null ){
            criteria = Criteria
                .where("numero").is(novo.getNumero())
                .and("logradouro.nome").is(novo.getLogradouro().getNome());
        } else {
            criteria = Criteria
                .where("numero").is(novo.getNumero())
                .and("letra").is(novo.getLetra())
                .and("logradouro.nome").is(novo.getLogradouro().getNome())
                .and("logradouro.tipoLogradouro.nome").is(novo.getLogradouro().getTipoLogradouro().getNome())

            ;
        }

        return new Query().addCriteria(criteria);
    }


}
