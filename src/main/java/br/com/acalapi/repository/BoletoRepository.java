package br.com.acalapi.repository;

import br.com.acalapi.entity.Boleto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoletoRepository extends MongoRepository<Boleto, String> {

}
