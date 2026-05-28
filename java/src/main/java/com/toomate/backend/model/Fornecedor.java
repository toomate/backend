package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico do fornecedor(incrementa automaticamente)", example = "1")
    @Column(name = "idFornecedor")
    private Integer id;
    @Schema(description = "Link para redirecionamento para o Whatsapp", example = "https://web.whatsapp.com/")
    private String linkWhatsapp;
    @Schema(description = "Razão social do fornecedor", example = "Atacado São Paulo")
    private String razaoSocial;
    @Schema(description = "Telefone do fornecedor", example = "11987654321")
    private String telefone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
