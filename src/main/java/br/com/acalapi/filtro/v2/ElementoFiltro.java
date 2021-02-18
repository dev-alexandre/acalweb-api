package br.com.acalapi.filtro.v2;

import lombok.Data;

@Data
public class ElementoFiltro {

    private String valor;
    private String[] order;
    private Boolean asc;
    private Integer prioridade;

}