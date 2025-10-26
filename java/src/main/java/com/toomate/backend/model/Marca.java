package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
public class Marca{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico da marca(incrementa automaticamente)", example = "1")
    private Integer idMarca;
    @Schema(description = "descricao", example = "camil")
    private String descricao;
    @Schema(description = "valor da medida", example = "200.5")
    private Double valorMedida;
    @Schema(description = "unidade da medida", example = "kg")
    private String unidadeMedida;
    @Schema(description = "nome da marca", example = "camil")
    private String nomeMarca;

    @ManyToOne
    @JoinColumn(name = "idInsumo")
    @Schema(description = "Insumo do qual a marca pertence")
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "idFornecedor")
    @Schema(description = "Fornecedor do qual a marca pertence")
    private Fornecedor fornecedor;

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

}
