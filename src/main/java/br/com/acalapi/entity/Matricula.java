package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "matricula")
public class Matricula extends AE {

    @Indexed
    @NotNull
    private Integer numero;

    @Indexed
    @NotNull
    private String letra;

    @NotNull
    private Logradouro logradouro;

    private String hidrometro;

    private List<Hidrometro> hidrometroList;

}