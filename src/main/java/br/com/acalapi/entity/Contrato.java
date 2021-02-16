package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "contrato")
public class Contrato extends AE {

    @Transient
    public static final String SEQUENCE_NAME = "contrato_sequence";

    private Long numero;

    private Cliente cliente;

    private Matricula matricula;

    private Matricula matriculaCorrespondencia;

    private Grupo grupo;

    private Boolean contratoPrincipal;

    private List<String> referencias;

    private List<Corte> corte;

    private boolean habilitado;

}