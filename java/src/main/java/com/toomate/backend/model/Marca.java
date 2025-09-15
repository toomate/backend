package com.toomate.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarca;
    private String descricao;
    private Double valorMedida;
    private String unidadeMedida;
    private Integer fkInsumo;
    private Integer fkFornecedor;

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorMedida() {
        return valorMedida;
    }

    public void setValorMedida(Double valorMedida) {
        this.valorMedida = valorMedida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getFkIngrediente() {
        return fkInsumo;
    }

    public void setFkIngrediente(Integer fkInsumo) {
        this.fkInsumo = fkInsumo;
    }

    public Integer getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(Integer fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }

    public Marca(Integer idMarca, String descricao, Double valorMedida, String unidadeMedida, Integer fkInsumo, Integer fkFornecedor) {
        this.idMarca = idMarca;
        this.descricao = descricao;
        this.valorMedida = valorMedida;
        this.unidadeMedida = unidadeMedida;
        this.fkInsumo = fkInsumo;
        this.fkFornecedor = fkFornecedor;
    }

    public Marca() {
    }
}
