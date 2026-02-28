package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDto {
    @NotBlank
    @Schema(description = "apelido do usuario para login", example = "lucas.dev")
    private String apelido;

    @NotBlank
    @Schema(description = "senha do usuario", example = "123")
    private String senha;

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
