package br.com.acalapi.repository.v1;

import br.com.acalapi.entity.ValorAgua;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ValorAguaRepository extends MongoRepository<ValorAgua, String> {

    ValorAgua findTopByOrderByDataDesc();

}
