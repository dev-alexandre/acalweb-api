package br.com.acalapi.enumeration;

import lombok.Getter;

@Getter
public enum ModuloEnum {

    MATRICULA("matricula"),
    LOGRADOURO("logradouro");

    public final String modulo;

    ModuloEnum(String modulo) {
        this.modulo = modulo;
    }
}
