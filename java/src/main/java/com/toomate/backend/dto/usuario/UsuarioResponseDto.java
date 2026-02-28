package com.toomate.backend.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioResponseDto {
    @Schema(description = "Id numerico do usuario (incrementa automaticamente)", example = "1")
    private Integer id;

    @Schema(description = "nome do usuario", example = "Lucas Silva")
    private String nome;

    @Schema(description = "apelido do usuario", example = "lucas.dev")
    private String apelido;

    @Schema(description = "usuario administrador", example = "true")
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
