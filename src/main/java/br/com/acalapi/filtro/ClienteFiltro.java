package br.com.acalapi.filtro;

import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ClienteFiltro extends Filtro {

    private ElementoFiltro nome;
    private ElementoFiltro documento;

}
