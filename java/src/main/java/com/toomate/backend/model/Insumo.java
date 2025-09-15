package com.toomate.backend.model;

import jakarta.persistence.*;

@Entity
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInsumo;
    private String nome;
    //    @ManyToOne
//    @JoinColumn(name = "idCategoria")
    private Integer fkCategoria;

    public Integer getIdIngrediente() {
        return idInsumo;
    }

    public void setIdIngrediente(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public Insumo(Integer idInsumo, String nome, Integer fkCategoria) {
        this.idInsumo = idInsumo;
        this.nome = nome;
        this.fkCategoria = fkCategoria;
    }

    public Insumo() {
    }
}
