package br.com.acalapi.entity.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Funcao implements GrantedAuthority {

    @Indexed(unique = true, sparse = true, name = "funcao.nome")
    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }

}