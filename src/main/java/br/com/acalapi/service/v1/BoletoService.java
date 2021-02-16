package br.com.acalapi.service.v1;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BoletoService {

    private void gerarPorReferencia() {
        Pageable pageable = PageRequest.of(0, 10);
        Query query = new Query().with(pageable);
    }

}
