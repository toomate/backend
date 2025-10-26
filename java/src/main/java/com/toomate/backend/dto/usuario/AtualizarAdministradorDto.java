package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class AtualizarAdministradorDto {
    @Schema(description = "usuário administrador", example = "1")
    private Boolean administrador;

    public Boolean getadministrador() {
        return administrador;
    }

    public void setadministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
