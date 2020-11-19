package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.AE;
import br.com.acalapi.entity.Logradouro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class Controller<T extends AE, F extends Filtro> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public abstract MongoRepository getRepository();

    private final Class<T> persistentClass;


    @SuppressWarnings("unchecked")
    public Controller() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        persistentClass = (Class<T>) type;
    }

    @RequestMapping(value="/deletar/{id}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable String id){
       getRepository().deleteById(id);
    }

    @RequestMapping(value="/editar", method = RequestMethod.PUT)
    public void editar(@RequestBody T t){
        getRepository().save(t);
    }

    @RequestMapping(value="/listar", method = RequestMethod.GET)
    public List<Logradouro> listar(){
        return getRepository().findAll(getSort());
    }

    @RequestMapping(value="/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody T t){
        getRepository().save(t);
    }

    @RequestMapping(value="/buscar/{id}", method = RequestMethod.GET)
    public T buscar(@PathVariable String id) {
        return mongoTemplate.findById(id, persistentClass);
    }

    @RequestMapping(value="/paginar", method = RequestMethod.POST)
    public Page<T> paginar(@RequestBody F pf) {

        Pageable pageable = getPageable(pf);
        Query query = new Query().with(pageable);

        return
            PageableExecutionUtils.getPage(
                mongoTemplate.find(query, persistentClass),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    @RequestMapping(value="paginar", method = RequestMethod.GET)
    public Page<T> paginar() {

        Pageable pageable = getPageable((F) new Filtro());
        Query query = new Query().with(pageable);

        return
            PageableExecutionUtils.getPage(
                mongoTemplate.find(query, persistentClass),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    public Pageable getPageable(F pf){
        if(getSort()==null) {
            return PageRequest.of(pf.getPage(), pf.getSize());
        } else {
            return PageRequest.of(pf.getPage(), pf.getSize(), getSort());
        }
    }

    public Pageable getPageable(F f, Sort sort) {
        return PageRequest.of(f.getPage(), f.getSize(), sort);
    }

    public Sort getSort() {
        return null;
    }
}