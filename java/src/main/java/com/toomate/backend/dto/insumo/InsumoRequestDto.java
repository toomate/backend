package com.toomate.backend.dto.insumo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InsumoRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    @Schema(description = "Nome do insumo(incrementa automaticamente)", example = "Arroz")
    private String nome;
    @Schema(description = "Quantidade minima de insumo para alertar", example = "5")
    private Integer qtdMinima;
    @Schema(description = "Quantidade de medida do insumo", example = "kg")
    private String unidadeMedida;
    @NotNull(message = "Categoria do insumo e obrigatoria.")
    private Integer fkCategoria;

    public InsumoRequestDto(String nome, Integer qtdMinima, String unidadeMedida, Integer fkCategoria) {
        this.nome = nome;
        this.qtdMinima = qtdMinima;
        this.unidadeMedida = unidadeMedida;
        this.fkCategoria = fkCategoria;
    }

    public InsumoRequestDto() {
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

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

}
