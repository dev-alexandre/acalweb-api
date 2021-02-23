package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Hidrometro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HidrometroRepository extends PagingAndSortingRepository<Hidrometro, String> {

}