package com.toomate.backend.model;

import jakarta.persistence.*;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIngrediente;
    private String nome;
    //    @ManyToOne
//    @JoinColumn(name = "idCategoria")
    private Integer fkCategoria;

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
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

    public Ingrediente(Integer idIngrediente, String nome, Integer fkCategoria) {
        this.idIngrediente = idIngrediente;
        this.nome = nome;
        this.fkCategoria = fkCategoria;
    }

    public Ingrediente() {
    }
}
