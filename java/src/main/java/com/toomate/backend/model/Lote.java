package com.toomate.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLote;
    private LocalDate dataValidade;
    private LocalDate dataEntrada;
    private Double precoUnitario;
    private Double quantidadeMedida;

    @ManyToOne
    @JoinColumn(name = "idMarca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getQuantidadeMedida() {
        return quantidadeMedida;
    }

    public void setQuantidadeMedida(Double quantidadeMedida) {
        this.quantidadeMedida = quantidadeMedida;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
