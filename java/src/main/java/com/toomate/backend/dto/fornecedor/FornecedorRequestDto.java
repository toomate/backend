package com.toomate.backend.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class FornecedorRequestDto {
    @NotBlank
    @Schema(description = "Link do whatsapp do fornecedor", example = "https://web.whatsapp.com/")
    private String link;
    @NotBlank
    @Schema(description = "Razão social do fornecedor", example = "Atacado São Paulo")
    private String razaoSocial;
    @NotBlank
    @Schema(description = "Link do whatsapp do fornecedor", example = "11987654321")
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
