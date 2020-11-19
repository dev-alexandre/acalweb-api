package br.com.acalapi.enumeration;

import lombok.Getter;

@Getter
public enum AcaoEnum {

    LISTAR("listar"),
    SALVAR("salvar"),
    EDITAR("editar"),
    DELETAR("deletar");

    private final String acao;

    AcaoEnum(String acao) {
        this.acao = acao;
    }

}
