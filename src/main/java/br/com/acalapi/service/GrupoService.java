package br.com.acalapi.service;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.enumeration.CategoriaEnum;
import br.com.acalapi.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository repository;

    public List<SelectDTO<Grupo>> listarSelect(){

        List<Grupo> grupos = repository.findAll(Sort.by("categoria.nome").ascending());

        grupos.stream().sorted(Comparator.comparing(Grupo::getNome)).collect(Collectors.toList());

        SelectDTO<Grupo> fundador = new SelectDTO<Grupo>();
        fundador.setTitle(CategoriaEnum.FUNDADOR.getNome());
        fundador.setValues(new ArrayList<>());

        SelectDTO<Grupo> efetivo = new SelectDTO<Grupo>();
        efetivo.setTitle(CategoriaEnum.EFETIVO.getNome());
        efetivo.setValues(new ArrayList<>());

        SelectDTO<Grupo> temporario = new SelectDTO<Grupo>();
        temporario.setTitle(CategoriaEnum.TEMPORARIO.getNome());
        temporario.setValues(new ArrayList<>());

        for (Grupo g: grupos) {

            if (g.getCategoria().getNome().equals("Sócio Fundador")) {
                fundador.getValues().add(g);

            } else if (g.getCategoria().getNome().equals("Sócio Efetivo")) {
                efetivo.getValues().add(g);

            } else if (g.getCategoria().getNome().equals("Sócio Temporário")) {
                temporario.getValues().add(g);
            }
        }

        List<SelectDTO<Grupo>> retorno = new ArrayList<>();

        if(!fundador.getValues().isEmpty()){
            retorno.add(fundador);
        }

        if(!efetivo.getValues().isEmpty()){
            retorno.add(efetivo);
        }

        if(!temporario.getValues().isEmpty()){
            retorno.add(temporario);
        }

        return retorno;
    }


}
