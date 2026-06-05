package com.toomate.backend.dto.rotina;

public class InsumoRotina {
    private Integer insumoId;
    private Integer quantidadeInsumo;
    private String unidadeMedida;

    public Integer getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }

    public Integer getQuantidadeInsumo() {
        return quantidadeInsumo;
    }

    public void setQuantidadeInsumo(Integer quantidadeInsumo) {
        this.quantidadeInsumo = quantidadeInsumo;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
