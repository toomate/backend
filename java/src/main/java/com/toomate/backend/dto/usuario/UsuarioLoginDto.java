package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioLoginDto {
    @Schema(description = "senha do usuário", example = "lucas")
    private String nome;
    @Schema(description = "senha do usuário", example = "lucas123")
    private String senha;

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
}
