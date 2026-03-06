package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id numerico do usuario (incrementa automaticamente)", example = "1")
    @Column(name = "idUsuario")
    private Integer id;

    @Schema(description = "nome do usuario", example = "Lucas Silva")
    @NotBlank
    private String nome;

    @Schema(description = "apelido do usuario usado no login", example = "lucas.dev")
    @NotBlank
    private String apelido;

    @Schema(description = "senha do usuario", example = "123")
    @NotBlank
    private String senha;

    @Schema(description = "usuario administrador", example = "true")
    private Boolean administrador;

    public Usuario(String nome, String apelido, String senha, Boolean administrador) {
        this.nome = nome;
        this.apelido = apelido;
        this.senha = senha;
        this.administrador = administrador;
    }

    public Usuario() {
    }

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
