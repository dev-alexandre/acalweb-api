package br.com.acalapi.controller;

import br.com.acalapi.controller.filtro.Filtro;
import br.com.acalapi.entity.security.Usuario;
import br.com.acalapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends Controller<Usuario, Filtro>{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

}
