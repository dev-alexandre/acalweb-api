package br.com.acalapi.repository;

import br.com.acalapi.entity.security.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {


}
