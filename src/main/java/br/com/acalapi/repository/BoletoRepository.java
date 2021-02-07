package br.com.acalapi.repository;

import br.com.acalapi.entity.financeiro.Boleto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BoletoRepository extends MongoRepository<Boleto, String> {

    @Query(value = "{'referencia': ?0}", count = true)
    long countByReferencia(String nome);

    Boleto findByNumero(String numero);
}
