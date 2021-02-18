package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "grupo")
public class Grupo extends AE {

    @Indexed
    private String nome;

    @Indexed
    private BigDecimal valor;

    @Indexed
    private BigDecimal valorSocio;
    private Categoria categoria;
}