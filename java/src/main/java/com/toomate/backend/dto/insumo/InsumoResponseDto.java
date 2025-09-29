package com.toomate.backend.dto.insumo;

import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;

public class InsumoResponseDto {
    private Integer idInsumo;
    private String nome;
    private Integer qtdMinima;
    private String unidadeMedida;
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
