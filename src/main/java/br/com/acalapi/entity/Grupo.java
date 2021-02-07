package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "grupo")
public class Grupo extends AE {

    private String nome;
    private BigDecimal valor;
    private BigDecimal valorSocio;
    private Categoria categoria;
}