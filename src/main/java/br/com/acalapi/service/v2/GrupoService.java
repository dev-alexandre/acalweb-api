package br.com.acalapi.service.v2;

import br.com.acalapi.dto.SelectDTO;
import br.com.acalapi.entity.Grupo;
import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.entity.Matricula;
import br.com.acalapi.enumeration.CategoriaEnum;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.GrupoFiltro;
import br.com.acalapi.filtro.LogradouroFiltro;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.repository.v2.GrupoRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoService extends ServiceV2<Grupo, GrupoFiltro> {

    @Autowired
    private GrupoRepository repository;

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQuery(GrupoFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterValor(filtro, query);
        filterValorSocio(filtro, query);
        filterCategoria(filtro, query);
        return query;
    }

    private void filterNome(GrupoFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null){
            query.addCriteria(
                Criteria.where("nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }

    private void filterValor(GrupoFiltro filtro, Query query) {
        if(filtro.getValor() != null && filtro.getValor().getValor() != null && !filtro.getValor().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("valor").is(new BigDecimal(filtro.getValor().getValor()))
            );
        }
    }

    private void filterValorSocio(GrupoFiltro filtro, Query query) {
        if(filtro.getValorSocio() != null && filtro.getValorSocio().getValor() != null && !filtro.getValorSocio().getValor().isEmpty()){
            query.addCriteria(
                Criteria.where("valorSocio").is(new BigDecimal(filtro.getValorSocio().getValor()))
            );
        }
    }


    private void filterCategoria(GrupoFiltro filtro, Query query) {
        if(filtro.getCategoria() != null && filtro.getCategoria().getValor() != null){
            query.addCriteria(
                Criteria.where("categoria.nome").regex(".*" + filtro.getCategoria().getValor() + ".*", "i")
            );
        }
    }


    @Override
    public void verificarSalvar(Grupo grupo) {
        Query query = new Query();
        query.addCriteria(
            Criteria
                .where("categoria.nome").is(grupo.getCategoria().getNome())
                .and("nome").is(grupo.getNome())
        );

        if(mongoOperations.exists(query, Grupo.class)){
            throw new ConflictDataException("Esse grupo já está cadastrado", HttpStatus.CONFLICT);
        }
    }

    @Override
    public void verificarEditar(Grupo atual) {
        Query query = new Query();

        query.addCriteria(
            Criteria
                .where("categoria.nome").is(atual.getCategoria().getNome())
                .and("nome").is(atual.getNome())
        );

        List<Grupo> anteriores = mongoOperations.find(query, Grupo.class);
        anteriores.forEach(anterior -> {
            if(anterior.getId().equals(atual.getId())){
                return;
            } else {
                throw new ConflictDataException("Esse Grupo já está cadastrado", HttpStatus.CONFLICT);
            }
        });

    }



    /** Olds */
    public List<SelectDTO<Grupo>> listarSelect() {

        List<Grupo> grupos = (List<Grupo>) getRepository().findAll(Sort.by("categoria.nome").ascending());

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

        for (Grupo g : grupos) {

            if (g.getCategoria().getNome().equals("Sócio Fundador")) {
                fundador.getValues().add(g);

            } else if (g.getCategoria().getNome().equals("Sócio Efetivo")) {
                efetivo.getValues().add(g);

            } else if (g.getCategoria().getNome().equals("Sócio Temporário")) {
                temporario.getValues().add(g);
            }
        }

        List<SelectDTO<Grupo>> retorno = new ArrayList<>();

        if (!fundador.getValues().isEmpty()) {
            retorno.add(fundador);
        }

        if (!efetivo.getValues().isEmpty()) {
            retorno.add(efetivo);
        }

        if (!temporario.getValues().isEmpty()) {
            retorno.add(temporario);
        }

        return retorno;
    }



}
