package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.entity.TipoLogradouro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LogradouroRepository extends PagingAndSortingRepository<Logradouro, String> {

    @Query("{ 'nome':{ $regex:?0, $options: 'i' }, 'tipoLogradouro':?1  }")
    List<Logradouro> findByNomeAndTipoLogradouro(String nome, TipoLogradouro tipoLogradouro);

    @Query("{ 'nome':{ $regex:?0, $options: 'i' }, 'tipoLogradouro.nome':{ $regex:?0, $options: 'i' }  }")
    boolean existsByNomeAndTipoLogradouro(String nome, TipoLogradouro tipoLogradouro);

}
