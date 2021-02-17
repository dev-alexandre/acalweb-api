package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.LogradouroFiltro;
import br.com.acalapi.repository.v2.LogradouroRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogradouroService extends ServiceV2<Logradouro, LogradouroFiltro> {

    @Autowired
    private LogradouroRepository repository;

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQuery(LogradouroFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterTipoLogradouro(filtro, query);
        return query;
    }

    private void filterNome(LogradouroFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null){
            query.addCriteria(
                Criteria.where("nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }

    private void filterTipoLogradouro(LogradouroFiltro filtro, Query query) {
        if (filtro.getTipoLogradouro() != null && filtro.getTipoLogradouro().getValor() != null) {
            query.addCriteria(
                Criteria.where("tipoLogradouro.nome").regex(".*" + filtro.getTipoLogradouro().getValor() + ".*", "i")
            );
        }
    }

    @Override
    public void verificarEditar(Logradouro atual) {

        List<Logradouro> anteriores = repository.findByNomeAndTipoLogradouro(atual.getNome(), atual.getTipoLogradouro());
        anteriores.forEach(anterior -> {
            if(anterior.getId().equals(atual.getId())){
                return;
            } else {
                throw new ConflictDataException("Esse Logradouro já está cadastrado", HttpStatus.CONFLICT);
            }
        });
    }

    @Override
    public void verificarSalvar(Logradouro logradouro) {

        /**@TODO escrever isso com exists*/
        if(repository.findByNomeAndTipoLogradouro(logradouro.getNome(), logradouro.getTipoLogradouro()).size() > 0){
            throw new ConflictDataException("Esse Logradouro já está cadastrado", HttpStatus.CONFLICT);
        }

    }


}
