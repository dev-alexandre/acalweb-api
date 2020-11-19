package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.AE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Controller<T extends AE, F extends Filtro> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public Controller() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        persistentClass = (Class<T>) type;
    }

    public Pageable getPageable(F filtro) {
        return PageRequest.of(filtro.getPage(), filtro.getSize());
    }

    public Pageable getPageable(F filtro, Sort sort) {
        return PageRequest.of(filtro.getPage(), filtro.getSize(), sort);
    }

    @RequestMapping(value="/buscar/{id}", method = RequestMethod.GET)
    public T buscar(@PathVariable String id) {
        throw new RuntimeException("Not Implement yet");
    }


    @RequestMapping(value="/paginar", method = RequestMethod.POST)
    public Page<T> paginar(@RequestBody F pf) {
        Pageable pageable = PageRequest.of(pf.getPage(), pf.getSize());
        Query query = new Query().with(pageable);

        return
                PageableExecutionUtils.getPage(
                        mongoTemplate.find(query, persistentClass),
                        pageable,
                        () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    @RequestMapping(value="paginar", method = RequestMethod.GET)
    public Page<T> paginar() {
        Pageable pageable = PageRequest.of(0, 10);
        Query query = new Query().with(pageable);

        return
            PageableExecutionUtils.getPage(
                mongoTemplate.find(query, persistentClass),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }



    /*
    private Criteria getCriteria(F filtro) throws IllegalArgumentException, IllegalAccessException {
        return null;
    }*/


}


    /*


    public Pageable getPageable(F filtro) {
        return PageRequest.of(filtro.getPage(), filtro.getSize());
    }

    public Pageable getPageable(F filtro, Sort sort) {
        return PageRequest.of(filtro.getPage(), filtro.getSize(), sort);
    }



    @RequestMapping(value="/buscar/{id}", method = RequestMethod.GET)
    public T buscar(@PathVariable String id) {
        return mongoTemplate.findById(id, persistentClass);
    }



    @RequestMapping(value="/paginar", method = RequestMethod.POST)
    public Page<T> paginar(@RequestBody F pf) {
        Pageable pageable;

        if(isNull(getSort())){
            pageable = getPageable(pf);
        } else {
            pageable = getPageable(pf, getSort());
        }

        Query query = new Query().with(pageable);

        return
            PageableExecutionUtils.getPage(
                mongoTemplate.find(query, persistentClass),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    private Criteria getCriteria(F filtro) throws IllegalArgumentException, IllegalAccessException {
        Criteria criteria = null;

        if(filtro != null) {
            for (Field f : filtro.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                if(f.get(filtro) != null) {
                    if(f.get(filtro) instanceof ElementoFiltro) {
                        getCriteria(filtro);
                    }

                    String value = (String) f.get(filtro);
                    String name = f.getName();

                    if(value !=null &&  name != null) {

                        criteria = Criteria.where(name).regex("^" + value);
                    }
                }

            }
        }

        return criteria;
    }

    public Sort getSort() {
        return null;
    }

    public Sort getSort(F filtro) throws IllegalArgumentException, IllegalAccessException{

        Sort sort = null;

        for (Field f : filtro.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            ElementoFiltro el = (ElementoFiltro) f.get(filtro);

            if(el != null) {
                if(el.getOrderAsc()!=null) {
                    if(el.getOrderAsc()) {

                        sort = Sort.by("nome").ascending();
                    }else {
                        sort = Sort.by("nome").descending();
                    }
                }
            }
        }

        return sort;
    }
    */

