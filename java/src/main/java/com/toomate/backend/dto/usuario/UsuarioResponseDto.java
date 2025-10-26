package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioResponseDto {
    @Schema(description = "Id númerico do usuário(incrementa automaticamente)", example = "1")
    private Integer id;
    @Schema(description = "nome do usário", example = "lucas")
    private String nome;
    @Schema(description = "usuário administrador", example = "1")
    private Boolean administrador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
