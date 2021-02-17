package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.security.Usuario;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.v1.UsuarioRepository;
import br.com.acalapi.service.v2.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends Controller<Usuario, Filtro> {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ClienteService service;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public void salvar(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        super.salvar(usuario);
    }

    @Override
    public Query getQueryDuplicidade(Usuario usuario) {
        return new Query().addCriteria(Criteria
                .where("email").is(usuario.getEmail()));
    }

}
