package br.com.acalapi.repository;

import br.com.acalapi.entity.Hidrometro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HidrometroRepository extends MongoRepository<Hidrometro, String> {

}