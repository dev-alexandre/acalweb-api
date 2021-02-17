package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "matricula")
public class Matricula extends AE {

    @Indexed
    private Integer numero;

    @Indexed
    private String letra;

    private String hidrometro;

    private Logradouro logradouro;

    private boolean possuiHidrometro;

    @DBRef
    private List<Hidrometro> hidrometroList;

}