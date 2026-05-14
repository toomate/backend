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
    @JoinColumn(name = "fkCategoria", referencedColumnName = "idCategoria")
    @Schema(description = "Categoria do insumo", example = "carboidrato")
    private Categoria categoria;
    @Schema(description = "Quantidade minima de insumo para alertar", example = "5")
    private Integer qtdMinima;
    @Schema(description = "Rotatividade do insumo", example = "true")
    private Boolean rotatividade;

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

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }

    public Insumo(Integer idInsumo, String nome, Categoria categoria, Integer qtdMinima, Boolean rotatividade) {
        this.idInsumo = idInsumo;
        this.nome = nome;
        this.categoria = categoria;
        this.qtdMinima = qtdMinima;
        this.rotatividade = rotatividade;
    }

    public Insumo() {
    }
}
