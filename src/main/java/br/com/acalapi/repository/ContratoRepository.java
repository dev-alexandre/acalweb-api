package br.com.acalapi.repository;

import br.com.acalapi.entity.Contrato;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContratoRepository extends MongoRepository<Contrato, String> {
}
