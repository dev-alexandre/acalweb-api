package br.com.acalapi.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BoletoService {


    private void gerarPorReferencia(){
        Pageable pageable = PageRequest.of(0, 10);
        Query query = new Query().with(pageable);



    }


        /*

        try {
            //Criteria criteria = Criteria.where(name).regex("^" + value);

            //if(criteria != null) {
            //    query.addCriteria(criteria);
            //}

            //Sort s = getSort(pf);

            //if(s!=null) {
            //   query.with(getSort(pf));
            //}

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return
                PageableExecutionUtils.getPage(
                mongoTemplate.find(query, persistentClass),
    pageable,
            () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), persistentClass));
        */


}
