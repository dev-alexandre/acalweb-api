package br.com.acalapi.filtro.v2;

import lombok.Data;

@Data
public class Filtro {

    private int size;
    private int page;
    private ElementoFiltro ativo;

    public Filtro() {
        this.size = 10;
        this.page = 0;
    }


}