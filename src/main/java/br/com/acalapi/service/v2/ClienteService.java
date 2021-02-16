package br.com.acalapi.service.v2;

import br.com.acalapi.entity.Cliente;
import br.com.acalapi.filtro.ClienteFiltro;
import br.com.acalapi.repository.v2.ClienteRepository;
import br.com.acalapi.service.ServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
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
    public Query getQuery(ClienteFiltro filtro, Query query) {
        filterNome(filtro, query);
        filterDocumento(filtro, query);

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

}
