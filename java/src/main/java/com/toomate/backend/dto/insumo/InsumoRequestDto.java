package com.toomate.backend.dto.insumo;

import com.toomate.backend.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public class InsumoRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;
    private Integer qtdMinima;
    private String unidadeMedida;
    private Integer fkCategoria;

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

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }
}
