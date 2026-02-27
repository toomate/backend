package com.toomate.backend.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaResponseDto {
    @Schema(description = "Id numerico da categoria", example = "1")
    private Integer idCategoria;

    @Schema(description = "Nome da categoria", example = "carboidrato")
    private String nome;

    @Schema(description = "Indica se a categoria possui alta rotatividade", example = "true")
    private Boolean rotatividade;

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

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }
}
