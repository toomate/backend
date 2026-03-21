package com.toomate.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRotina")
    private Integer id;
    private String titulo;
    @OneToMany(mappedBy = "rotina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotinaInsumo> rotinaInsumos;

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
}
