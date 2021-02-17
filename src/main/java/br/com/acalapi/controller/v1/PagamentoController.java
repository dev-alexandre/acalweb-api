package br.com.acalapi.controller.v1;

import br.com.acalapi.controller.Controller;
import br.com.acalapi.entity.financeiro.Pagamento;
import br.com.acalapi.filtro.v2.Filtro;
import br.com.acalapi.repository.v1.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController extends Controller<Pagamento, Filtro> {

    @Autowired
    private PagamentoRepository repository;

    @Override
    public MongoRepository getRepository() {
        return repository;
    }

    @Override
    public Query getQueryDuplicidade(Pagamento pagamento) {
        return null;
    }


    @Override
    public Sort getSort() {
        return
                Sort.by("responsavel.nome");
    }

}
