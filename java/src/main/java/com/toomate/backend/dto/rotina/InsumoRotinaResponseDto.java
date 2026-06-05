package com.toomate.backend.dto.rotina;

public class InsumoRotinaResponseDto {
    private Integer id;
    private String nome;
    private Integer quantidade;
    private String unidadeMedida;

    public InsumoRotinaResponseDto(Integer id, String nome, Integer quantidade, String unidadeMedida) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}