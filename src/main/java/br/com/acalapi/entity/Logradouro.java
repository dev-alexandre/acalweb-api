package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logradouro")
@EqualsAndHashCode(callSuper = false)
public class Logradouro extends AE {

    @Indexed
    private String nome;

    private TipoLogradouro tipoLogradouro;

}