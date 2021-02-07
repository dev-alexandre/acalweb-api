package br.com.acalapi.entity.financeiro;

import br.com.acalapi.entity.security.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Debito {

    private String nome;
    private BigDecimal valor;

    @DBRef
    private Usuario responsavel;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyyHHmmss")
    public LocalDateTime dataCriacao;

}