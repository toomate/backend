package com.toomate.backend.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ClienteRequestDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
    @Pattern(regexp = "\\d{5}-\\d{3}")
    private String cep;
    private String logradouro;
    private String bairro;

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

}
