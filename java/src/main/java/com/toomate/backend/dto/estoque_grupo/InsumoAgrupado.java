package com.toomate.backend.dto.estoque_grupo;

import java.time.LocalDate;

public class InsumoAgrupado {
    private Integer idInsumo;
    private String nomeMarca;
    private Double quantidadeMedida; // vai vir do Lote
    private String unidadeMedida; // vai vir do insumo
    private LocalDate dataValidade; // lote

    public InsumoAgrupado(Integer idInsumo, String nomeMarca, Double quantidadeMedida, String unidadeMedida, LocalDate dataValidade) {
        this.idInsumo = idInsumo;
        this.nomeMarca = nomeMarca;
        this.quantidadeMedida = quantidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.dataValidade = dataValidade;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
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
}
