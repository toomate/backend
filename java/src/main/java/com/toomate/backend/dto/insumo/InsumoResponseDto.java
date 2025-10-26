package com.toomate.backend.dto.insumo;

import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

public class InsumoResponseDto {
    @Schema(description = "Id númerico do insumo(incrementa automaticamente)", example = "1")
    private Integer idInsumo;
    @Schema(description = "Nome do insumo(incrementa automaticamente)", example = "Arroz")
    private String nome;
    @Schema(description = "Quantidade minima de insumo para alertar", example = "5")
    private Integer qtdMinima;
    @Schema(description = "Quantidade de medida do insumo", example = "kg")
    private String unidadeMedida;
    @Schema(description = "Categoria do insumo", example = "carboidrato")
    private CategoriaResponseDto categoria;

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdMinima() {
        return qtdMinima;
    }

    public void setQtdMinima(Integer qtdMinima) {
        this.qtdMinima = qtdMinima;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public CategoriaResponseDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDto categoria) {
        this.categoria = categoria;
    }
}
