package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequestDto {
    @NotBlank
    @Schema(description = "nome do usário", example = "lucas")
    private String nome;
    @NotBlank
    @Schema(description = "senha do usuário", example = "lucas123")
    private String senha;
    @NotNull
    @Schema(description = "usuário administrador", example = "1")
    private Boolean administrador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public UsuarioRequestDto(String nome, String senha, Boolean administrador) {
        this.nome = nome;
        this.senha = senha;
        this.administrador = administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
