package com.toomate.backend.dto.rotina;

import jakarta.validation.constraints.NotNull;

public class RotinaRequestDto {
    @NotNull
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
