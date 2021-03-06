package br.com.acalapi.filtro;

import br.com.acalapi.entity.TipoLogradouro;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LogradouroFiltro extends Filtro {

    private ElementoFiltro nome;
    private ElementoFiltro tipoLogradouro;

}
