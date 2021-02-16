package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "valorAgua")
public class ValorAgua extends AE {

    private String valor;
    private LocalDateTime data;
    private Integer litrosGratuidade;

}