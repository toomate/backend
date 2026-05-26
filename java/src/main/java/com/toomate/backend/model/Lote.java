package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico do lote(incrementa automaticamente)", example = "1")
    private Integer idLote;
    @Schema(description = "data de validade)", example = "2025-10-19")
    private LocalDate dataValidade;
    @Schema(description = "data de entrada)", example = "2025-10-19")
    private LocalDate dataEntrada;
    @Schema(description = "preço unitário)", example = "25.99")
    @Column(name = "precoUnit")
    private Double precoUnitario;
    @Schema(description = "quantidade da medida)", example = "5")
    private Double quantidadeMedida;
    @Schema(description = "Unidade da medida", example = "kg")
    private String unidadeMedida;
    @Schema(description = "Quantidade total de insumos daquele lote", example = "100")
    private Integer quantidadeAtual;
    private Integer quantidadeOriginal;

    @ManyToOne
    @JoinColumn(name = "fkMarca", referencedColumnName = "idMarca")
    @Schema(description = "Marca dos insumos do lote")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "fkUsuario", referencedColumnName = "idUsuario")
    @Schema(description = "Usuario que cadastrou o lote")
    private Usuario usuario;

    public Lote(Integer idLote, LocalDate dataValidade, LocalDate dataEntrada, Double precoUnitario, Double quantidadeMedida, String unidadeMedida, Integer quantidadeAtual, Integer quantidadeOriginal, Marca marca, Usuario usuario) {
        this.idLote = idLote;
        this.dataValidade = dataValidade;
        this.dataEntrada = dataEntrada;
        this.precoUnitario = precoUnitario;
        this.quantidadeMedida = quantidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeOriginal = quantidadeOriginal;
        this.marca = marca;
        this.usuario = usuario;
    }

    public Lote(){}

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

    public void adicionarQuantidadeMedida(Integer quantidadeMedida){
        this.quantidadeAtual += quantidadeMedida;
    }

    public void removerQuantidadeMedida(Integer quantidadeMedida){
        this.quantidadeAtual -= quantidadeMedida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public Integer getQuantidadeOriginal() {
        return quantidadeOriginal;
    }

    public void setQuantidadeOriginal(Integer quantidadeOriginal) {
        this.quantidadeOriginal = quantidadeOriginal;
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
