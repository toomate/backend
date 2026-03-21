package com.toomate.backend.dto.rotina;

import java.util.List;

public class RotinaInsumoRequest {
    private Integer fkRotina;
    private List<InsumoRotina> insumos;

    public Integer getFkRotina() {
        return fkRotina;
    }

    public void setFkRotina(Integer fkRotina) {
        this.fkRotina = fkRotina;
    }

    public List<InsumoRotina> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<InsumoRotina> insumos) {
        this.insumos = insumos;
    }
}
