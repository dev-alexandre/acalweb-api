package br.com.acalapi.service;

import br.com.acalapi.entity.AE;
import br.com.acalapi.enumeration.constante.CheckboxEnum;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class ServiceV2<T extends AE, F extends Filtro> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String ATIVO = "ativo";

    @Autowired
    protected MongoTemplate mongoOperations;

    public abstract Query getQuery(F filtro, Query query);
    public abstract PagingAndSortingRepository<T, String> getRepository();

    private final Class<T> persistentClass;

    protected ServiceV2() {
        persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public boolean isValid(ElementoFiltro parametro){
        return (parametro != null && parametro.getValor()!= null && !parametro.getValor().isEmpty());
    }

    public List<T> listar(F filtro){
        filtro = iniciarFiltro(filtro);
        Query query = new Query();

        filterAtivo(filtro, query);
        getQuery(filtro, query);

        return mongoOperations.find( query , persistentClass);
    }



    public Page<T> paginar(F filtro) {

        filtro = iniciarFiltro(filtro);
        Pageable pageable = getPageable(filtro);
        Query query = new Query().with(pageable);

        filterAtivo(filtro, query);
        getQuery(filtro, query);

        return paginar(pageable, query);
    }

    public Page<T> paginar(int offset, int limit){

        Pageable pageable = PageRequest.of(offset, limit);
        Query query = new Query().with(pageable);

        return paginar(pageable, query);
    }



    private Page<T> paginar(Pageable pageable, Query query){
        return
            PageableExecutionUtils.getPage(
                mongoOperations.find(query, persistentClass),
                pageable,
                () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), persistentClass));
    }

    private F iniciarFiltro(F filtro) {
        if (filtro == null) {
            return (F) new Filtro();
        }

        return filtro;
    }

    private void filterAtivo(F filtro, Query query) {

        if(filtro != null && filtro.getAtivo()!= null) {
            if (filtro.getAtivo().getValor() != null) {

                if (filtro.getAtivo().getValor().equals(CheckboxEnum.SIM.getValor())) {
                    query.addCriteria(
                            new Criteria().orOperator(
                                    Criteria.where(ATIVO).is(true),
                                    Criteria.where(ATIVO).is(null)
                            )
                    );
                } else if (filtro.getAtivo().getValor().equals(CheckboxEnum.NAO.getValor())) {
                    query.addCriteria(
                            Criteria.where(ATIVO).is(false)
                    );
                }
            }
        }
    }

    public Sort ordenarLista(F filtro) {
        Sort.Direction direction = Sort.Direction.ASC;
        List<ElementoFiltro> orders= new ArrayList<>();

        for(Field field : filtro.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                ElementoFiltro el = (ElementoFiltro) field.get(filtro);
                if(el != null){

                    if (el.getAsc() != null && !el.getAsc()) {
                        direction = Sort.Direction.DESC;
                    }

                    if(el.getOrder() != null){
                        orders.add(el);
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        orders.sort(Comparator.comparing(ElementoFiltro::getPrioridade));
        if(orders.size() == 0){
            return Sort.by(Sort.Direction.ASC, "id");
        }
        String[] stringOrders =  concat(orders);

        return Sort.by(direction, stringOrders);
    }

    private Pageable getPageable(F filtro) {
        Sort sort;
        Sort.Direction direction = Sort.Direction.ASC;
        List<ElementoFiltro> orders = new ArrayList<>();

        for(Field field : filtro.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                ElementoFiltro el = (ElementoFiltro) field.get(filtro);
                if(el != null){

                    if (el.getAsc() != null && !el.getAsc()) {
                        direction = Sort.Direction.DESC;
                    }

                    if(el.getOrder() != null){
                        orders.add(el);
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        orders.sort(Comparator.comparing(ElementoFiltro::getPrioridade));
        String[] stringOrders = concat(orders);

        if(orders.size() == 0){
            sort = Sort.by(Sort.Direction.ASC, "id");
        } else {
            sort = Sort.by(direction, stringOrders);
        }

        return PageRequest.of(filtro.getPage(), filtro.getSize(), sort);
    }

    private String[] concat(List<ElementoFiltro> orders){

        int size = (int) orders.stream().flatMap(o -> Arrays.stream(o.getOrder())).count();
        String[] stringOrders = new String[size];

        int position = 0;
        for (ElementoFiltro o : orders) {
            int insidePosition = 0;
            for (String i : o.getOrder()) {
                stringOrders[position++] = o.getOrder()[insidePosition++];
            }
        }

        return stringOrders;
    }

    public List<T> listarTodos(){
        logger.info("Listar todos");
        return (List<T>) getRepository().findAll();
    }

    public void deletar(String id) {
        logger.info("delete por id", id);
        getRepository().deleteById(id);
    }

    public void salvar(T t){
        logger.info("dado salvo", t);
        verificarSalvar(t);
        getRepository().save(t);
    }

    @Deprecated
    public void salvar(List<T> ts){
        ts.forEach(t -> verificarSalvar(t));

       // getRepository().save(ts);
    }

    public void editar(T t){
        logger.info("dado editado", t);
        verificarEditar(t);
        getRepository().save(t);
    }

    public T buscar(String id){
        logger.info("buscar", id);
        return getRepository().findById(id).orElse(null);
    }

    public void verificarSalvar(T t){ }
    public void verificarEditar(T t){ }


}
