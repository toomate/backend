package com.toomate.backend.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
