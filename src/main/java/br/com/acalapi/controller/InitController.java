package br.com.acalapi.controller;

import br.com.acalapi.entity.security.Funcao;
import br.com.acalapi.entity.security.Usuario;
import br.com.acalapi.repository.UsuarioRepository;
import br.com.acalapi.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/init")
public class InitController{

    @Autowired
    private UserDetailService repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value="/pass", method = RequestMethod.GET)
    public void buscarAtual() {

            Usuario usuario = new Usuario();
            usuario.setEmail("alexandre@gmail.com");
            usuario.setPassword(passwordEncoder.encode("123456"));
            usuario.setAccountNonExpired(true);
            usuario.setAccountNonLocked(true);
            usuario.setEnabled(true);
            usuario.setName("alexandre");
            usuario.setTitle("programador");
            usuario.setFuncoes(new ArrayList<>());

            usuario.setFuncoes(
                Arrays.asList(
                    new Funcao("HOME"),
                    new Funcao("ADMINISTRACAO"))
            );

            userRepository.save(usuario);

    }

}