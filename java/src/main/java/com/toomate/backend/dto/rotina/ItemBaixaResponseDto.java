package com.toomate.backend.dto.rotina;

import java.util.List;

public class ItemBaixaResponseDto {
    private Integer insumoId;
    private String nomeInsumo;
    private Integer quantidadeNecessaria;
    private String unidadeMedida;
    private List<LoteConsumidoResponseDto> lotes;

    public Integer getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }

    public String getNomeInsumo() {
        return nomeInsumo;
    }

    public void setNomeInsumo(String nomeInsumo) {
        this.nomeInsumo = nomeInsumo;
    }

    public Integer getQuantidadeNecessaria() {
        return quantidadeNecessaria;
    }

    public void setQuantidadeNecessaria(Integer quantidadeNecessaria) {
        this.quantidadeNecessaria = quantidadeNecessaria;
    }

    public List<LoteConsumidoResponseDto> getLotes() {
        return lotes;
    }

    public void setLotes(List<LoteConsumidoResponseDto> lotes) {
        this.lotes = lotes;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
