package com.toomate.backend.dto.insumo;

import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class InsumoResponseDto {
    @Schema(description = "Id númerico do insumo(incrementa automaticamente)", example = "1")
    private Integer idInsumo;
    @Schema(description = "Nome do insumo(incrementa automaticamente)", example = "Arroz")
    private String nome;
    @Schema(description = "Quantidade minima de insumo para alertar", example = "5")
    private Integer qtdMinima;
    @Schema(description = "Rotatividade do insumo", example = "true")
    private Boolean rotatividade;
    @Schema(description = "Categoria do insumo", example = "carboidrato")
    private CategoriaResponseDto categoria;
    @Schema(description = "Diz se o insumo está ativo", example = "true")
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

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

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }

    public CategoriaResponseDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDto categoria) {
        this.categoria = categoria;
    }
}
