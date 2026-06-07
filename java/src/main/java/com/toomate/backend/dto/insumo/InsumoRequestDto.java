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
    @Schema(description = "Rotatividade do insumo", example = "true")
    private Boolean rotatividade;
    @NotNull(message = "Categoria do insumo e obrigatoria.")
    private Integer fkCategoria;
    @Schema(description = "Diz se o insumo está ativo", example = "true")
    private Boolean ativo;

    public InsumoRequestDto(String nome, Integer qtdMinima, Boolean rotatividade, Integer fkCategoria, Boolean ativo) {
        this.nome = nome;
        this.qtdMinima = qtdMinima;
        this.rotatividade = rotatividade;
        this.fkCategoria = fkCategoria;
        this.ativo = ativo;
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

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
