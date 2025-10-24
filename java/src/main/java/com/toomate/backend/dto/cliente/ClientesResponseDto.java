package com.toomate.backend.dto.cliente;


public class ClientesResponseDto {
    Integer idCliente;
    private String nome;
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
