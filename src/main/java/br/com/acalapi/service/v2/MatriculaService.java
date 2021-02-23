package br.com.acalapi.service.v2;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Cliente;
import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.LogradouroFiltro;
import br.com.acalapi.filtro.MatriculaFiltro;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.repository.v2.MatriculaRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService extends ServiceV2<Matricula, MatriculaFiltro> {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private MongoTemplate mongoOperations;


    @Override
    public Query getQuery(MatriculaFiltro filtro, Query query) {
        filterNumero(filtro, query);
        filterLetra(filtro, query);
        filterHidrometro(filtro, query);
        filterLogradouro(filtro, query);
        filterTipoLogradouro(filtro, query);
        filterIdLogradouro(filtro, query);

        return query;
    }

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }

    private void filterNumero(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getNumero())){
            query.addCriteria(
                Criteria.where("numero").is(Integer.valueOf(filtro.getNumero().getValor()))
            );
        }
    }

    private void filterLetra(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getLetra())){
            query.addCriteria(
                Criteria.where("letra").regex(".*" + filtro.getLetra().getValor() + ".*", "i")
            );
        }
    }

    private void filterHidrometro(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getHidrometro())){
            query.addCriteria(
                    Criteria.where("hidrometro").regex(".*" + filtro.getHidrometro().getValor() + ".*", "i")
            );
        }
    }

    private void filterLogradouro(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getLogradouro())){
            query.addCriteria(
                Criteria.where("logradouro.nome").regex(".*" + filtro.getLogradouro().getValor() + ".*", "i")
            );
        }
    }

    private void filterTipoLogradouro(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getTipoLogradouro())){
            query.addCriteria(
                Criteria.where("logradouro.tipoLogradouro.nome").regex(".*" + filtro.getTipoLogradouro().getValor() + ".*", "i")
            );
        }
    }

    private void filterIdLogradouro(MatriculaFiltro filtro, Query query) {
        if(super.isValid(filtro.getIdLogradouro())){
            query.addCriteria(
                Criteria.where("logradouro.id").is(filtro.getIdLogradouro().getValor())
            );
        }
    }

    @Override
    public void verificarEditar(Matricula atual) {
        Query query = new Query();

        query.addCriteria(
            Criteria
                .where("logradouro.tipoLogradouro.nome").is(atual.getLogradouro().getTipoLogradouro().getNome())
                .and("logradouro.nome").is(atual.getLogradouro().getNome())
                .and("numero").is(atual.getNumero())
        );

        if(atual.getLetra() != null){
            query.addCriteria(
                Criteria.where("letra").is(atual.getLetra())
            );
        }

        List<Matricula> anteriores =  mongoOperations.find(query, Matricula.class);
        anteriores.forEach(anterior -> {
            if(anterior.getId().equals(atual.getId())){
                return;
            } else {
                throw new ConflictDataException("Esse matricula já está cadastrado", HttpStatus.CONFLICT);
            }
        });
    }

    @Override
    public void verificarSalvar(Matricula matricula) {

        Query query = new Query();

        query.addCriteria(
            Criteria
                .where("logradouro.tipoLogradouro.nome").is(matricula.getLogradouro().getTipoLogradouro().getNome())
                .and("logradouro.nome").is(matricula.getLogradouro().getNome())
                .and("numero").is(matricula.getNumero())
        );

        if(matricula.getLetra() != null){
            query.addCriteria(
                Criteria.where("letra").is(matricula.getLetra())
            );
        }

        if(mongoOperations.exists(query, Matricula.class)){
            throw new ConflictDataException("Esse matrícula já está cadastrado", HttpStatus.CONFLICT);
        }

    }






    /** Olds */
    public List<Matricula> listarMatriculasHidrometro() {
        return mongoOperations.find(new Query().addCriteria(new Criteria().where("possuiHidrometro").is(true)), Matricula.class);
    }

    public List<Matricula> listarPorReferencia(String referencia) {
        Criteria criteria =
                new Criteria()
                        .where("hidrometros.referencia").nin(referencia).and("possuiHidrometro").is(true);

        return mongoOperations.find(new Query().addCriteria(criteria), Matricula.class);
    }

    public List<SelectDTO<Matricula>> selecionar(String name) {

        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("logradouro.nome").regex(".*" + name + ".*", "i"),
                Criteria.where("logradouro.tipoLogradouro.nome").regex(".*" + name + ".*", "i")
        );

        List<Matricula> matriculas = mongoOperations.find(new Query().limit(30).addCriteria(criteria), Matricula.class);
        List<SelectDTO<Matricula>> dados = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (Matricula m : matriculas) {
            if (!nomes.contains(m.getLogradouro().getTipoLogradouro().getNome())) {
                nomes.add(m.getLogradouro().getTipoLogradouro().getNome());

                SelectDTO<Matricula> d = new SelectDTO<>();
                d.setTitle(m.getLogradouro().getTipoLogradouro().getNome());
                dados.add(d);
            }
        }

        for (SelectDTO<Matricula> d : dados) {

            if (d.getValues() == null) {
                d.setValues(new ArrayList<>());
            }

            d.getValues().addAll(matriculas.stream().filter(f ->
                    f.getLogradouro().getTipoLogradouro().getNome().equals(d.getTitle())).collect(Collectors.toList()));
        }

        return dados;
    }



}
