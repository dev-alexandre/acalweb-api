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
@Document(collection = "hidrometro")
public class Hidrometro extends AE {

    @Indexed
    private String referencia;

    private String matricula;

    private Integer atual;

    private Integer anterior;

    private BigDecimal valorLitroAgua;

    private Integer litrosGratuidade;

    private BigDecimal valor;
}