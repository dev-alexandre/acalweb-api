package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Grupo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GrupoRepository extends PagingAndSortingRepository<Grupo, String> {
}
