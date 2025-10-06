package com.toomate.backend.dto.lote;

import java.time.LocalDate;

public class LoteRequestDto {
    private LocalDate dataValidade;
    private Double precoUnitario;
    private Double quantidadeMedida;
    private LocalDate dataEntrada;
    private Integer fkMarca;
    private Integer fkUsuario;

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnit) {
        this.precoUnitario = precoUnit;
    }

    public Double getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Integer getFkMarca() {
        return fkMarca;
    }

    public void setFkMarca(Integer fkMarca) {
        this.fkMarca = fkMarca;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}
