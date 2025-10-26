package com.toomate.backend.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    @Schema(description = "nome do fornecedor", example = "lucas")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
