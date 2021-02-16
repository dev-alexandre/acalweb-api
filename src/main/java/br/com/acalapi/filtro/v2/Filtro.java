package br.com.acalapi.filtro.v2;

import lombok.Data;

@Data
public class Filtro {

    private int size;
    private int page;
    private boolean ativo;

    public Filtro() {
        this.size = 10;
        this.page = 0;
        this.ativo = true;
    }

}