package br.com.acalapi.enumeration.constante;

import lombok.Getter;

@Getter
public enum CheckboxEnum {

    SIM("sim"),
    INDETERMINADO("indeterminado"),
    NAO("nao");

    private final String valor;

    CheckboxEnum(String valor) {
        this.valor = valor;
    }

}
