package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Representa uma categoria de alimento.")
public class Categoria {
    public Categoria(Integer idCategoria, String nome) {
        this.idCategoria = idCategoria;
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico da categoria(incrementa automaticamente)", example = "1")
    private Integer idCategoria;
    @Schema(description = "Nome da categoria", example = "carboidrato")
    private String nome;

    private Boolean rotatividade;

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria() {
    }

    public Categoria(Integer idCategoria, String nome, Boolean rotatividade) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.rotatividade = rotatividade;
    }

    public void inverterRotatividade(){
        rotatividade = !rotatividade;
    }
}
