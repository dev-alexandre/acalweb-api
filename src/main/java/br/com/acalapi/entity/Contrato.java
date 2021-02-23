package br.com.acalapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "contrato")
public class Contrato extends AE {

    @Transient
    public static final String SEQUENCE_NAME = "contrato_sequence";

    @Indexed
    private long numero;

    private Cliente cliente;

    private Matricula matricula;

    private Matricula matriculaCorrespondencia;

    private Grupo grupo;

    private Boolean contratoPrincipal;

    private List<String> referencias;

    private List<Corte> corte;

    @Indexed
    private boolean habilitado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyyHHmmss")
    public LocalDateTime encerrado;

}