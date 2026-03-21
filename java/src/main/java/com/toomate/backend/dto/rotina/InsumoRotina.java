package com.toomate.backend.dto.rotina;

import com.toomate.backend.model.Insumo;

public class InsumoRotina {
    private Integer insumoId;
    private Double quantidadeMedida;

    public Integer getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }

    public Double getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }
}
