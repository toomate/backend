package com.toomate.backend.dto.fornecedor;

import com.toomate.backend.model.Boleto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;

public class FornecedorResponseDto {
    @Schema(description = "Id do fornecedor", example = "1")
    private Integer idFornecedor;
    @Schema(description = "Link do whatsapp do fornecedor", example = "https://web.whatsapp.com/")
    private String linkWhatsapp;
    @Schema(description = "Razão social do fornecedor", example = "Atacado São Paulo")
    private String razaoSocial;
    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getLinkWhatsapp() {
        return linkWhatsapp;
    }

    public void setLinkWhatsapp(String linkWhatsapp) {
        this.linkWhatsapp = linkWhatsapp;
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
