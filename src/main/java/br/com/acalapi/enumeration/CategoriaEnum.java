package br.com.acalapi.enumeration;

import lombok.Getter;

@Getter
public enum CategoriaEnum {

    FUNDADOR("Sócio Fundador"),
    EFETIVO("Sócio Efetivo"),
    TEMPORARIO("Sócio Temporário");

    private final String nome;

    CategoriaEnum(String nome) {
        this.nome = nome;
    }

}
