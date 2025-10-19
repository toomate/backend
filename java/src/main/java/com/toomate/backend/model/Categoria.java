package com.toomate.backend.model;

import io.swagger.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Representa uma categoria de alimento.")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico da categoria(incrementa automaticamente)", example = "1")
    private Integer idCategoria;
    @Schema(description = "Nome da categoria", example = "Proteína")
    private String nome;

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

    public Categoria(Integer idCategoria, String nome) {
        this.idCategoria = idCategoria;
        this.nome = nome;
    }
}
