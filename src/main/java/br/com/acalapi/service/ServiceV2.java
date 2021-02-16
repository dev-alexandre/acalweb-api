package br.com.acalapi.service;

import br.com.acalapi.entity.AE;
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
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class ServiceV2<T extends AE, F extends Filtro> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MongoTemplate mongoOperations;

    public abstract Query getQuery(F filtro, Query query);
    public abstract PagingAndSortingRepository getRepository();

    private final Class<T> persistentClass;
    private final Class<F> filterClass;

    public ServiceV2() {
        persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        filterClass = (Class<F>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public Page<T> paginar(F filtro) {

        filtro = iniciarFiltro(filtro);
        Pageable pageable = getPageable(filtro);
        Query query = new Query();

        filterAtivo(filtro, query);
        getQuery(filtro, query);

        return paginar(pageable, query);
    }

    public Page<T> paginar(int offset, int limit){
        Filtro filtro = new Filtro();

        Pageable pageable = PageRequest.of(offset, limit);
        Query query = new Query();

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
        if (filtro.isAtivo()) {
            query.addCriteria(
                new Criteria().orOperator(
                    Criteria.where("ativo").is(true),
                    Criteria.where("ativo").is(null)
                )
            );
        } else {
            query.addCriteria(
                Criteria.where("ativo").is(false)
            );
        }
    }

    private Pageable getPageable(F filtro) {
        Sort sort = null;

        String sortName = "";
        Sort.Direction direction;

        for(Field field : filtro.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                Object el = field.get(filtro);
                if(el instanceof ElementoFiltro){
                    Boolean direcao = ((ElementoFiltro) el).getAsc();
                    String nameFiled = field.getName();

                    if(direcao != null){
                        sortName = nameFiled;
                        if(direcao){
                            direction = Sort.Direction.ASC;
                        }else {
                            direction = Sort.Direction.DESC;
                        }

                        return PageRequest.of(filtro.getPage(), filtro.getSize(), Sort.by("nome").descending());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return PageRequest.of(filtro.getPage(), filtro.getSize());
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
        getRepository().save(t);
    }

    public void salvar(List<T> t){
        logger.info("dados salvos", t);
        getRepository().save(t);
    }

    public void editar(T t){
        logger.info("dado editado", t);
        getRepository().save(t);
    }

    public T buscar(String id){
        logger.info("buscar", id);
        return (T) getRepository().findById(id).orElse(null);
    }


}
