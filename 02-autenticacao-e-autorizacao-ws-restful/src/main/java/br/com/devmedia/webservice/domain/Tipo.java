package br.com.devmedia.webservice.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Tipo {

    CLIENTE("Cliente"),
    FUNCIONARIO("Funcionario"),
    ADMINISTRADOR("Administrador");

    private String valor;

    Tipo(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

}
