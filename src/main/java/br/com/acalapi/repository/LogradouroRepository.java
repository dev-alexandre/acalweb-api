package br.com.acalapi.repository;

import br.com.acalapi.entity.Logradouro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogradouroRepository extends MongoRepository<Logradouro, String> {
}
