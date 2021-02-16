package br.com.acalapi.service.v1;

import br.com.acalapi.entity.sequence.DatabaseSequence;
import br.com.acalapi.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class DatabaseSequenceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ContratoRepository repository;

    public long generateSequence(String seqName) {

        DatabaseSequence counter = mongoTemplate.findAndModify(
                new Query().addCriteria(
                        Criteria.where("_id_" +
                                LocalDate.now().getYear() +
                                LocalDate.now().getMonth()
                        ).is(seqName)
                ),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
