package br.com.acalapi.filtro;

import br.com.acalapi.entity.Categoria;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=true)
public class GrupoFiltro extends Filtro {

    private ElementoFiltro nome;
    private ElementoFiltro valor;
    private ElementoFiltro valorSocio;
    private ElementoFiltro categoria;

}
