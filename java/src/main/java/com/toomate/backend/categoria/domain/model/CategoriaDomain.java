package com.toomate.backend.categoria.domain.model;

public class CategoriaDomain {
    private Integer id;
    private String nome;
    private Boolean rotatividade;

    public CategoriaDomain() {
    }

    public CategoriaDomain(Integer id, String nome, Boolean rotatividade) {
        this.id = id;
        this.nome = nome;
        this.rotatividade = rotatividade;
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

    public Boolean getRotatividade() {
        return rotatividade;
    }

    public void setRotatividade(Boolean rotatividade) {
        this.rotatividade = rotatividade;
    }
}
