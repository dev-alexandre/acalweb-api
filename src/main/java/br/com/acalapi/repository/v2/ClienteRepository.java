package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, String > {

    @Query("{ 'nome':{ $regex:?0, $options: 'i' } }")
    List<Cliente> findByname(String nome);
}
