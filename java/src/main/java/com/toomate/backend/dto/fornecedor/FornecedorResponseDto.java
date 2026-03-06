package com.toomate.backend.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;

public class FornecedorResponseDto {
    @Schema(description = "Id do fornecedor", example = "1")
    private Integer idFornecedor;

    @Schema(description = "Link de contato do fornecedor", example = "https://wa.me/5511987654321")
    private String link;

    @Schema(description = "Razao social do fornecedor", example = "Atacado Sao Paulo")
    private String razaoSocial;

    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
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
