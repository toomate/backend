package com.toomate.backend.dto.fornecedor;

import jakarta.validation.constraints.NotBlank;

public class FornecedorRequestDto {
    @NotBlank
    private String link;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String telefone;

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
