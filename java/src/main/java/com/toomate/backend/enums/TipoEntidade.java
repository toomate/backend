package com.toomate.backend.enums;

public enum TipoEntidade {
    LOTE("lote"),
    INSUMO("insumo"),
    DIVIDA("divida"),
    BOLETO("boleto");

    private String tipo;

    TipoEntidade(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }
}
