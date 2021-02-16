package br.com.acalapi.controller.v1;

import br.com.acalapi.entity.security.Funcao;
import br.com.acalapi.entity.security.Usuario;
import br.com.acalapi.repository.UsuarioRepository;
import br.com.acalapi.service.v1.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/init")
public class InitController {

    @Autowired
    private UserDetailService repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    public void buscarAtual() {

        Usuario alexandre = new Usuario();
        alexandre.setEmail("edvaldo");
        alexandre.setPassword(passwordEncoder.encode("$Hunt3r22"));
        alexandre.setAccountNonExpired(true);
        alexandre.setAccountNonLocked(true);
        alexandre.setCredentialsNonExpired(true);
        alexandre.setEnabled(true);
        alexandre.setName("Edvaldo Araujo");
        alexandre.setTitle("Secretario");
        alexandre.setFuncoes(new ArrayList<>());

        alexandre.setFuncoes(
            Arrays.asList(
                new Funcao("HOME"),
                new Funcao("CADASTROS"),
                new Funcao("ANALISE"),
                new Funcao("FINANCEIRO"),
                new Funcao("CAIXA"),
                new Funcao("OUTROS"),
                new Funcao("ADMINISTRACAO")
            ));


        userRepository.save(alexandre);

    }


}
