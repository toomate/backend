package com.toomate.backend.dto.rotina;

public class RotinaInsumoRequest {
    private Integer id;
    private Integer fkRotina;
    private Integer fkInsumo;
    private Double quantidadeMedida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkRotina() {
        return fkRotina;
    }

    public void setFkRotina(Integer fkRotina) {
        this.fkRotina = fkRotina;
    }

    public Integer getFkInsumo() {
        return fkInsumo;
    }

    public void setFkInsumo(Integer fkInsumo) {
        this.fkInsumo = fkInsumo;
    }

    public Double getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }
}
