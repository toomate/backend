package com.toomate.backend.dto.rotina;

import java.time.LocalDate;

public class LoteConsumidoResponseDto {
    private Integer loteId;
    private String marca;
    private LocalDate validade;
    private Integer quantidadeConsumida;
    private String unidadeMedida;
    private Integer quantidadeLotesRetirados;
    private Integer quantidadeUnidadesConsumidas;

    public Integer getQuantidadeLotesRetirados() {
        return quantidadeLotesRetirados;
    }

    public void setQuantidadeLotesRetirados(Integer quantidadeLotesRetirados) {
        this.quantidadeLotesRetirados = quantidadeLotesRetirados;
    }

    public Integer getQuantidadeUnidadesConsumidas() {
        return quantidadeUnidadesConsumidas;
    }

    public void setQuantidadeUnidadesConsumidas(Integer quantidadeUnidadesConsumidas) {
        this.quantidadeUnidadesConsumidas = quantidadeUnidadesConsumidas;
    }

    public Integer getLoteId() {
        return loteId;
    }

    public void setLoteId(Integer loteId) {
        this.loteId = loteId;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Integer getQuantidadeConsumida() {
        return quantidadeConsumida;
    }

    public void setQuantidadeConsumida(Integer quantidadeConsumida) {
        this.quantidadeConsumida = quantidadeConsumida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
