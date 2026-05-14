package com.toomate.backend.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDto {
    @NotBlank(message = "O nome nao pode ser vazio.")
    @Schema(description = "Nome da categoria", example = "carboidrato")
    private String nome;

    public CategoriaRequestDto() {
    }

    public CategoriaRequestDto(String nome) {
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
