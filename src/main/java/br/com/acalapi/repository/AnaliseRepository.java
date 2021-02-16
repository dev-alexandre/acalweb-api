package br.com.acalapi.repository;

import br.com.acalapi.entity.Analise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnaliseRepository extends MongoRepository<Analise, String> {

    Analise findByReferencia(String referencia);
}
