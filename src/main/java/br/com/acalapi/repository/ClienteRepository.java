package br.com.acalapi.repository;

import br.com.acalapi.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    @Query("{ 'nome':{ $regex:?0, $options: 'i' } }")
    List<Cliente> findByname(String nome);
}
