package br.com.acalapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Contrato extends AE{

    private Cliente cliente;
    private Matricula matricula;
    private Matricula matriculaCorrespondencia;
    private Grupo grupo;
    private Boolean indContratoPrincipal;
    private BigDecimal valor;
    private boolean habilitado;

    private List<Referencia> referencias;

}