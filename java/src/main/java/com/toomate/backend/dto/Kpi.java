package com.toomate.backend.dto;

public class Kpi {
    private String id;
    private String titulo;
    private Double valor;

    public Kpi(String id, String titulo, Double valor) {
        this.id = id;
        this.titulo = titulo;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
