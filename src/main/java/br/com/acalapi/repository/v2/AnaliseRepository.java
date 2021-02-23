package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Analise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnaliseRepository extends PagingAndSortingRepository<Analise, String> {

    Analise findByReferencia(String referencia);
}
