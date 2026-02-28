package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioTokenDto {
    @Schema(description = "Id numerico do usuario (incrementa automaticamente)", example = "1")
    private Integer userId;

    @Schema(description = "nome do usuario", example = "Lucas Silva")
    private String nome;

    @Schema(description = "apelido do usuario", example = "lucas.dev")
    private String apelido;

    @Schema(description = "Token do usuario")
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
