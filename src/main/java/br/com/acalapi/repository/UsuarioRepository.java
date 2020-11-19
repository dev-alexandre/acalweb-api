package br.com.acalapi.repository;

import br.com.acalapi.entity.Matricula;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UsuarioRepository extends MongoRepository<Matricula, String> {


}
