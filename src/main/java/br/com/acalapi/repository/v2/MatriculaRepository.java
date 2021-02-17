package br.com.acalapi.repository.v2;

import br.com.acalapi.entity.Matricula;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MatriculaRepository extends PagingAndSortingRepository<Matricula, String> {

    @Query("{ 'logradouro.tipoLogradouro.nome':{ $regex:?0, $options: 'i' } }")
    List<Matricula> findByname(String nome, Sort sort);

}
