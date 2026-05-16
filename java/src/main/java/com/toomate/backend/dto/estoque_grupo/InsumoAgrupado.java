package com.toomate.backend.dto.estoque_grupo;

import java.time.LocalDate;

public class InsumoAgrupado {
    private Integer idInsumo;
    private Integer idMarca;
    private String nomeMarca;
    private Integer idLote;
    private Double quantidadeMedida;
    private Integer quantidadeTotal; // vai vir do Lote// vai vir do Lote
    private Integer quantidadeMinima;
    private String unidadeMedida; // vai vir do insumo
    private LocalDate dataValidade; // lote

    public InsumoAgrupado(Integer idInsumo, Integer idMarca, String nomeMarca, Integer idLote, Double quantidadeMedida, Integer quantidadeTotal, Integer quantidadeMinima, String unidadeMedida, LocalDate dataValidade) {
        this.idInsumo = idInsumo;
        this.idMarca = idMarca;
        this.nomeMarca = nomeMarca;
        this.idLote = idLote;
        this.quantidadeMedida = quantidadeMedida;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeMinima = quantidadeMinima;
        this.unidadeMedida = unidadeMedida;
        this.dataValidade = dataValidade;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
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

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
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
