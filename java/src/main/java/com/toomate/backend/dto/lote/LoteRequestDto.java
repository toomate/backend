package com.toomate.backend.dto.lote;

import com.toomate.backend.model.Marca;
import com.toomate.backend.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class LoteRequestDto {
    @Schema(description = "data de validade)", example = "2025-10-19")
    private LocalDate dataValidade;
    @Schema(description = "preço unitário)", example = "25.99")
    private Double precoUnitario;
    @Schema(description = "quantidade da medida)", example = "5")
    private Double quantidadeMedida;
    @Schema(description = "unidade da medida", example = "kg")
    private String unidadeMedida;
    @Schema(description = "data de entrada)", example = "2025-10-19")
    private LocalDate dataEntrada;
    @Schema(description = "Quantidade total de insumos daquele lote", example = "100")
    private Integer quantidadeTotal;

    @Schema(description = "FK da marca dos insumos do lote")
    private Integer fkMarca;

    @Schema(description = "FK do usuario que cadastrou o lote")
    private Integer fkUsuario;


    public LoteRequestDto(LocalDate dataValidade, Double precoUnitario, Double quantidadeMedida, String unidadeMedida, LocalDate dataEntrada, Integer quantidadeTotal, Integer fkMarca, Integer fkUsuario) {
        this.dataValidade = dataValidade;
        this.precoUnitario = precoUnitario;
        this.quantidadeMedida = quantidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.dataEntrada = dataEntrada;
        this.quantidadeTotal = quantidadeTotal;
        this.fkMarca = fkMarca;
        this.fkUsuario = fkUsuario;
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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
