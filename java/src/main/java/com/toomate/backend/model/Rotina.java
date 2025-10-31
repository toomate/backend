package com.toomate.backend.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Rotina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "fkInsumo")
    private Insumo insumo;
    private Integer quantidadeMinima;
    private LocalTime periodo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public LocalTime getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalTime periodo) {
        this.periodo = periodo;
    }
}
