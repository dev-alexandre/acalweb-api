package br.com.acalapi.repository.v1;

import br.com.acalapi.entity.Hidrometro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HidrometroRepository extends MongoRepository<Hidrometro, String> {

}