package br.com.acalapi.service;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repository;


    public List<SelectDTO<Matricula>> listarSelect(String name){

        List<Matricula> matriculas = repository.findByname(name, Sort.by("logradouro.tipoLogradouro.nome").ascending());
        List<SelectDTO<Matricula>> dados = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (Matricula m: matriculas) {
            if(!nomes.contains(m.getLogradouro().getTipoLogradouro().getNome())){
                nomes.add(m.getLogradouro().getTipoLogradouro().getNome());

                SelectDTO<Matricula> d = new SelectDTO<>();
                d.setTitle(m.getLogradouro().getTipoLogradouro().getNome());
                dados.add(d);
            }
        }

        for (SelectDTO<Matricula> d: dados) {

            if(d.getValues() == null){
                d.setValues(new ArrayList<>());
            }

            d.getValues().addAll(matriculas.stream().filter(f ->
                    f.getLogradouro().getTipoLogradouro().getNome().equals(d.getTitle())).collect(Collectors.toList()));
        }

        return dados;
    }


}
