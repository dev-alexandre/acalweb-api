package br.com.acalapi.entity.financeiro;

import br.com.acalapi.entity.AE;
import br.com.acalapi.entity.Contrato;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "boleto")
public class Boleto extends AE {

    @Transient
    public static final String SEQUENCE_NAME = "boleto_sequence";

    @DBRef
    private Contrato contrato;

    @DBRef
    private List<Pagamento> pagamentos;

    private String numero;

    private String referencia;

    private String status;

    private List<Debito> debitos;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy")
    public LocalDate vencimento;

}