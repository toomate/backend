package com.toomate.backend.dto.cliente;


import io.swagger.v3.oas.annotations.media.Schema;

public class ClientesResponseDto {
    @Schema(description = "Id do cliente", example = "1")
    Integer idCliente;
    @Schema(description = "Nome do cliente", example = "lucas")
    private String nome;
    @Schema(description = "telefone do cliente", example = "11987654321")
    private String telefone;

    public ClientesResponseDto(Integer idCliente, String nome, String telefone) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
}
