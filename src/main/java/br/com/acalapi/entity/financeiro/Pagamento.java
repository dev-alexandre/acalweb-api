package br.com.acalapi.entity.financeiro;

import br.com.acalapi.entity.AE;
import br.com.acalapi.entity.security.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "pagamento")
public class Pagamento extends AE {

    private String motivo;

    private BigDecimal valor;

    private Usuario responsavel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    private LocalDate criacao;

}