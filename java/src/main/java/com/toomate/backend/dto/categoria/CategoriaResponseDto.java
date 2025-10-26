package com.toomate.backend.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaResponseDto {
    @Schema(description = "Id númerico da categoria", example = "1")
    private Integer idCategoria;
    @Schema(description = "Nome do fornecedor", example = "lucas")
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
}
