package br.com.acalapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Corte {

    private String usuario;

    private boolean ligacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyyHHmmss")
    private LocalDateTime data;

}