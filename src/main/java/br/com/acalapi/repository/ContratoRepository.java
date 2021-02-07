package br.com.acalapi.repository;

import br.com.acalapi.entity.Contrato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ContratoRepository extends MongoRepository<Contrato, String> {

    @Query(value = "{'grupo.categoria.nome': ?0}", count = true)
    long countByCategoria(String nome);


}
