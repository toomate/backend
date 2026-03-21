package com.toomate.backend.dto.estoque_grupo;

import java.time.LocalDate;

public class EstoqueVencimento {
    private Integer idLote;
    private String nome;
    private String nomeMarca;
    private Double quantidadeMedida;
    private String unidadeMedida;
    private LocalDate dataValidade;

    public EstoqueVencimento(Integer idLote, String nome, String nomeMarca, Double quantidadeMedida, String unidadeMedida, LocalDate dataValidade) {
        this.idLote = idLote;
        this.nome = nome;
        this.nomeMarca = nomeMarca;
        this.quantidadeMedida = quantidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.dataValidade = dataValidade;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
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

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }
}
