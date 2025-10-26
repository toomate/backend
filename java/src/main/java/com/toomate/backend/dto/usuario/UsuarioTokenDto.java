package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioTokenDto {
    @Schema(description = "Id númerico do usuário(incrementa automaticamente)", example = "1")
    private Integer userId;
    @Schema(description = "nome do usário", example = "lucas")
    private String nome;
    @Schema(description = "Token do usário")
    private String token;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
