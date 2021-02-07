package br.com.acalapi.repository;

import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.entity.TipoLogradouro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LogradouroRepository extends MongoRepository<Logradouro, String> {

    @Query("{ 'nome':{ $regex:?0, $options: 'i' }, 'tipoLogradouro':?1  }")
    List<Logradouro> findByNomeAndTipoLogradouro(String nome, TipoLogradouro tipoLogradouro);

}
