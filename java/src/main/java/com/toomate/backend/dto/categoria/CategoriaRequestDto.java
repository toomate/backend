package com.toomate.backend.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDto {
    @NotBlank(message = "O nome nao pode ser vazio.")
    @Schema(description = "Nome da categoria", example = "carboidrato")
    private String nome;

    @Schema(description = "Indica se a categoria possui alta rotatividade", example = "true")
    private Boolean rotatividade;

    public CategoriaRequestDto() {
    }

    public CategoriaRequestDto(String nome) {
        this.nome = nome;
    }

    public CategoriaRequestDto(String nome, Boolean rotatividade) {
        this.nome = nome;
        this.rotatividade = rotatividade;
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
