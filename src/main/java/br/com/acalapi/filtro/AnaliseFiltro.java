package br.com.acalapi.filtro;

import br.com.acalapi.entity.Coleta;
import br.com.acalapi.filtro.v2.ElementoFiltro;
import br.com.acalapi.filtro.v2.Filtro;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnaliseFiltro extends Filtro {

    private ElementoFiltro referencia;
    private ElementoFiltro parametro;
    private ElementoFiltro exigido;
    private ElementoFiltro analisado;
    private ElementoFiltro conforme;

}
