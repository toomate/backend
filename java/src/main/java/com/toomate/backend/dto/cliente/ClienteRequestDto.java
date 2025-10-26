package com.toomate.backend.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ClienteRequestDto {
    @NotBlank
    @Schema(description = "Nome do cliente", example = "lucas")
    private String nome;
    @NotBlank
    @Schema(description = "telefone do cliente", example = "11987654321")
    private String telefone;
    @Pattern(regexp = "\\d{5}-\\d{3}")
    @Schema(description = "cep do cliente", example = "01001000")
    private String cep;
    @Schema(description = "logradouro do cliente", example = "Rua São Paulo")
    private String logradouro;
    @Schema(description = "bairro do cliente", example = "Jardim Brasil")
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
