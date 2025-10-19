package com.toomate.backend.model;

import io.swagger.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico do fornecedor(incrementa automaticamente)", example = "1")
    private Integer id;
    @Schema(description = "Link para redirecionamento para o Whatsapp")
    @Schema(description = "Id númerico do fornecedor(incrementa automaticamente)", example = "1")
    private Integer idFornecedor;
    @Schema(description = "Link para redirecionamento para o Whatsapp")
    private String link;
    @Schema(description = "Razão social do fornecedor", example = "Atacado São Paulo")
    private String razaoSocial;
    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public Integer getId() {
        return idFornecedor;
    }

    public void setId(Integer id) {
        this.idFornecedor = id;
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
