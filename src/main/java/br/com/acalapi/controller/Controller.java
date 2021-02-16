package br.com.acalapi.controller;

import br.com.acalapi.entity.AE;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.v2.Filtro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class Controller<T extends AE, F extends Filtro> {

    @Autowired
    protected MongoTemplate mongoOperations;

    public abstract MongoRepository getRepository();
    public abstract Query getQueryDuplicidade(T t);

    private final Class<T> persistentClass;

    public Controller() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        persistentClass = (Class<T>) type;
    }


    @RequestMapping(value = "/salvar-todos", method = RequestMethod.POST)
    public void salvar(@RequestBody List<T> ts) {
        ts.forEach(this::verificarDuplicidade);
        getRepository().saveAll(ts);
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody T t) {
        verificarDuplicidade(t);
        getRepository().save(t);
    }

    @RequestMapping(value = "/editar", method = RequestMethod.PUT)
    public void editar(@RequestBody T t) {
        verificarDuplicidade(t);
        getRepository().save(t);
    }

    public void verificarDuplicidade(T novo) {
        if (novo == null) {
            throw new ConflictDataException("Essa solicitação não pode ser processada", HttpStatus.BAD_REQUEST);
        }

        if (getQueryDuplicidade(novo) == null) {
            return;
        }

        Query query = getQueryDuplicidade(novo);
        T antigo = mongoOperations.findOne(query, persistentClass);
        ;

        if (antigo == null) {
            return;
        }

        if (novo.getId() == null) {
            if (antigo != null) {
                throw new ConflictDataException(this.persistentClass.getSimpleName() + " já cadastrado", HttpStatus.CONFLICT);
            }
        } else {
            if (!novo.getId().equals(antigo.getId())) {
                throw new ConflictDataException(this.persistentClass.getSimpleName() + " já cadastrado", HttpStatus.CONFLICT);
            }
        }
    }

    @RequestMapping(value = "/deletar/{id}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable String id) {
        getRepository().deleteById(id);
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<T> listar() {
        return getRepository().findAll(getSort());
    }


    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public T buscar(@PathVariable String id) {
        return mongoOperations.findById(id, persistentClass);
    }

    @RequestMapping(value = "/paginar", method = RequestMethod.POST)
    public Page<T> paginar(@RequestBody F pf) {

        Pageable pageable = getPageable(pf);
        Query query = getQuery(pageable, pf);

        return
                PageableExecutionUtils.getPage(
                        mongoOperations.find(query, persistentClass),
                        pageable,
                        () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    public Page<T> paginar() {

        F filtro = (F) new Filtro();
        Pageable pageable = getPageable(filtro);
        Query query = getQuery(pageable, filtro);


        return
                PageableExecutionUtils.getPage(
                        mongoOperations.find(query, persistentClass),
                        pageable,
                        () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }
    @RequestMapping(value = "paginar", method = RequestMethod.GET)

    public Query getQuery(Pageable pageable, Filtro filtro) {

        if (filtro.isAtivo()) {
            return new Query()
                .addCriteria(
                    new Criteria().orOperator(
                        Criteria.where("ativo").is(true),
                        Criteria.where("ativo").is(null)
                )
            ).with(pageable);
        }

        return new Query().with(pageable);
    }

    public Pageable getPageable(F pf) {
        Sort sort = getSort();

        if (sort == null) {
            return PageRequest.of(pf.getPage(), pf.getSize());
        } else {
            return PageRequest.of(pf.getPage(), pf.getSize(), sort);
        }
    }

    public Pageable getPageable(F f, Sort sort) {
        return PageRequest.of(f.getPage(), f.getSize(), sort);
    }

    public Sort getSort() {
        return null;
    }

    public Query getQueryName(Pageable pageable, Filtro filtro) {
        return new Query().with(pageable);
    }

}