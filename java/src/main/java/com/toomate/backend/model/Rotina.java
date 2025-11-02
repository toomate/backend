package com.toomate.backend.model;

import jakarta.persistence.*;

@Entity
public class Rotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private Integer quantidadeMedida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Integer quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }

}
