package br.com.acalapi.filtro;

import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ContratoFiltro extends Filtro {

    private ElementoFiltro nome;
    private ElementoFiltro matricula;
    private ElementoFiltro principal;
    private ElementoFiltro grupo;
    private ElementoFiltro valor;

}
