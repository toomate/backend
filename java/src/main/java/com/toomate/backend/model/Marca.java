package com.toomate.backend.model;

import jakarta.persistence.*;

@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarca;
    private String nomeMarca;

    @ManyToOne
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "idFornecedor")
    private Fornecedor fornecedor;

    private Integer fkInsumo;
    private Integer fkFornecedor;

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

    public Integer getFkInsumo() {
        return fkInsumo;
    }

    public void setFkInsumo(Integer fkInsumo) {
        this.fkInsumo = fkInsumo;
    }

    public Integer getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(Integer fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }
}
