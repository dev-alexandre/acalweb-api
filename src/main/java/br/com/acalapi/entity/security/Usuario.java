package br.com.acalapi.entity.security;

import br.com.acalapi.entity.AE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "seguranca")
public class Usuario extends AE implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private String name;
    private String title;

    private List<Funcao> funcoes;

    @Transient
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return funcoes;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
