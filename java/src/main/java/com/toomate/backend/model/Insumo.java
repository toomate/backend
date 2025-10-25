package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico do insumo(incrementa automaticamente)", example = "1")
    private Integer idInsumo;
    @Schema(description = "Nome do insumo(incrementa automaticamente)", example = "Arroz")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    private Integer qtdMinima;
    private String unidadeMedida;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getQtdMinima() {
        return qtdMinima;
    }

    public void setQtdMinima(Integer qtdMinima) {
        this.qtdMinima = qtdMinima;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Insumo(Integer idInsumo, String nome, Categoria categoria, Integer qtdMinima, String unidadeMedida) {
        this.idInsumo = idInsumo;
        this.nome = nome;
        this.categoria = categoria;
        this.qtdMinima = qtdMinima;
        this.unidadeMedida = unidadeMedida;
    }

    public Insumo() {
    }
}
