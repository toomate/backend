package com.toomate.backend.dto.estoque_grupo;

import java.time.LocalDate;

public class EstoqueGeral {
    private Integer idCategoria;
    private String nomeCategoria;
    private Integer idInsumo;
    private String nomeInsumo;
    private Integer qtdMinima;
    private Integer idMarca;
    private String nomeMarca;
    private Integer idLote;
    private Integer quantidadeTotal; // vai vir do Lote
    private Double quantidadeMedida; // vai vir do Lote
    private String unidadeMedida; // vai vir do lote
    private LocalDate dataValidade;

    public EstoqueGeral(Integer idCategoria, String nomeCategoria, Integer idInsumo, String nomeInsumo, Integer qtdMinima, Integer idMarca, String nomeMarca, Integer idLote, Integer quantidadeTotal, Double quantidadeMedida, String unidadeMedida, LocalDate dataValidade) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.idInsumo = idInsumo;
        this.nomeInsumo = nomeInsumo;
        this.qtdMinima = qtdMinima;
        this.idMarca = idMarca;
        this.nomeMarca = nomeMarca;
        this.idLote = idLote;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeMedida = quantidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.dataValidade = dataValidade;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNomeInsumo() {
        return nomeInsumo;
    }

    public void setNomeInsumo(String nomeInsumo) {
        this.nomeInsumo = nomeInsumo;
    }

    public Integer getQtdMinima() {
        return qtdMinima;
    }

    public void setQtdMinima(Integer qtdMinima) {
        this.qtdMinima = qtdMinima;
    }

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

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public Double getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }
}
