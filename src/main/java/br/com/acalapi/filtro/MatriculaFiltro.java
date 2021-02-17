package br.com.acalapi.filtro;

import br.com.acalapi.entity.Logradouro;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class MatriculaFiltro extends Filtro {

    private ElementoFiltro numero;
    private ElementoFiltro letra;
    private ElementoFiltro hidrometro;
    private ElementoFiltro logradouro;
    private ElementoFiltro tipoLogradouro;

}
