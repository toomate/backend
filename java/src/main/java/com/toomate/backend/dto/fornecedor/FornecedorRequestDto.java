package com.toomate.backend.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class FornecedorRequestDto {
    @NotBlank(message = "A razao social nao pode ser vazia.")
    @Schema(description = "Razao social do fornecedor", example = "Atacado Sao Paulo")
    private String razaoSocial;

    @NotBlank(message = "O telefone nao pode ser vazio.")
    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public FornecedorRequestDto() {
    }

    public FornecedorRequestDto(String razaoSocial, String telefone) {
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
