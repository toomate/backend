package com.toomate.backend.dto.rotina;

import jakarta.validation.constraints.NotNull;

public class RotinaRequestDto {
    @NotNull
    private String titulo;
    @NotNull
    private Integer quantidadeMedida;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Integer quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }
}
