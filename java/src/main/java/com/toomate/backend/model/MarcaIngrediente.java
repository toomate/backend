package com.toomate.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MarcaIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarca;
    private String descricao;
    private Double valorMedida;
    private String unidadeMedida;
    private Integer fkIngrediente;
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
        return fkIngrediente;
    }

    public void setFkIngrediente(Integer fkIngrediente) {
        this.fkIngrediente = fkIngrediente;
    }

    public Integer getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(Integer fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }

    public MarcaIngrediente(Integer idMarca, String descricao, Double valorMedida, String unidadeMedida, Integer fkIngrediente, Integer fkFornecedor) {
        this.idMarca = idMarca;
        this.descricao = descricao;
        this.valorMedida = valorMedida;
        this.unidadeMedida = unidadeMedida;
        this.fkIngrediente = fkIngrediente;
        this.fkFornecedor = fkFornecedor;
    }

    public MarcaIngrediente() {
    }
}
