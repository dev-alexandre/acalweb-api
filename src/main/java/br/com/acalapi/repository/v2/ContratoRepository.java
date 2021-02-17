package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Contrato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContratoRepository extends PagingAndSortingRepository<Contrato, String> {

    @Query(value = "{'grupo.categoria.nome': ?0}", count = true)
    long countByCategoria(String nome);

}
