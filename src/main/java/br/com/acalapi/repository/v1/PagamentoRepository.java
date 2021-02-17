package br.com.acalapi.repository.v1;

import br.com.acalapi.entity.financeiro.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagamentoRepository extends MongoRepository<Pagamento, String> {

}
