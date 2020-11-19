package br.com.acalapi.repository;

import br.com.acalapi.entity.Grupo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrupoRepository extends MongoRepository<Grupo, String> {
}
