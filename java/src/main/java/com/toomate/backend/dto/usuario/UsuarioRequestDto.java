package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioRequestDto {
    @NotBlank
    @Schema(description = "nome do usuario", example = "lucas")
    private String nome;

    @NotBlank
    @Schema(description = "senha do usuario", example = "lucas123")
    private String senha;

    @NotNull
    @Schema(description = "usuario administrador", example = "true")
    private Boolean administrador;

    public UsuarioRequestDto() {
    }

    public UsuarioRequestDto(String nome, String senha, Boolean administrador) {
        this.nome = nome;
        this.senha = senha;
        this.administrador = administrador;
    }

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

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}