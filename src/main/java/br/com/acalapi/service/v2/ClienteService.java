package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.exception.ConflictDataException;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.repository.v2.ClienteRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends ServiceV2<Cliente, ClienteFiltro> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public PagingAndSortingRepository getRepository() {
        return repository;
    }


    @Override
    public void verificarEditar(Cliente atual) {
        Cliente anterior = repository.findByDocumento(atual.getDocumento());

        if(anterior.getId().equals(atual.getId())){
            return;
        } else {
            throw new ConflictDataException("Esse documento já está cadastrado", HttpStatus.CONFLICT);
        }

    }

    @Override
    public void verificarSalvar(Cliente cliente) {

        if(repository.existsByDocumento(cliente.getDocumento())){
            throw new ConflictDataException("Esse documento já está cadastrado", HttpStatus.CONFLICT);
        }

    }


    @Override
    public Query getQuery(ClienteFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterDocumento(filtro, query);
        filterSocio(filtro, query);
        filterLetra(filtro, query);

        return query;
    }

    private void filterNome(ClienteFiltro filtro, Query query) {
        if(filtro.getNome() != null && filtro.getNome().getValor() != null){
            query.addCriteria(
                Criteria.where("nome").regex(".*" + filtro.getNome().getValor() + ".*", "i")
            );
        }
    }

    private void filterDocumento(ClienteFiltro filtro, Query query) {
        if (filtro.getDocumento() != null && filtro.getDocumento().getValor() != null) {
            query.addCriteria(
                Criteria.where("documento").regex(".*" + filtro.getDocumento().getValor() + ".*", "i")
            );
        }
    }

    private void filterSocio(ClienteFiltro filtro, Query query) {
        if (filtro.getSocio() != null && filtro.getSocio().getValor() != null) {
            query.addCriteria(
                Criteria.where("socio").regex( filtro.getSocio().getValor() , "i")
            );
        }
    }

    private void filterLetra(ClienteFiltro filtro, Query query) {
        if (filtro.getLetra() != null && filtro.getLetra().getValor() != null) {
            query.addCriteria(
                Criteria.where("letra").regex( filtro.getLetra().getValor() , "i")
            );
        }
    }

}
