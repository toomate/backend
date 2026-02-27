package com.toomate.backend.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class FornecedorRequestDto {
    @NotBlank(message = "O link nao pode ser vazio.")
    @Schema(description = "Link de contato do fornecedor", example = "https://wa.me/5511987654321")
    private String link;

    @NotBlank(message = "A razao social nao pode ser vazia.")
    @Schema(description = "Razao social do fornecedor", example = "Atacado Sao Paulo")
    private String razaoSocial;

    @NotBlank(message = "O telefone nao pode ser vazio.")
    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public FornecedorRequestDto() {
    }

    public FornecedorRequestDto(String link, String razaoSocial, String telefone) {
        this.link = link;
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
