package com.toomate.backend.enums;

public enum Dia {
    SEGUNDA("Segunda"),
    TERCA("Terca"),
    QUARTA("Quarta"),
    QUINTA("Quinta"),
    SEXTA("Sexta"),
    SABADO("Sabado");

    private String desc;

    Dia(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}
