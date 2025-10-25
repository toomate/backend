package com.toomate.backend.dto.lote;

import com.toomate.backend.dto.marca.MarcaResponseDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;

import java.time.LocalDate;

public class LoteResponseDto {
    private Integer idLote;
    private LocalDate dataValidade;
    private Double precoUnitario;
    private Double quantidadeMedida;
    private LocalDate dataEntrada;
    private UsuarioResponseDto usuario;
    private MarcaResponseDto marca;

    public LoteResponseDto(){}

    public LoteResponseDto(Integer idLote, LocalDate dataValidade, Double precoUnitario, Double quantidadeMedida, LocalDate dataEntrada, UsuarioResponseDto usuario, MarcaResponseDto marca) {
        this.idLote = idLote;
        this.dataValidade = dataValidade;
        this.precoUnitario = precoUnitario;
        this.quantidadeMedida = quantidadeMedida;
        this.dataEntrada = dataEntrada;
        this.usuario = usuario;
        this.marca = marca;
    }

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

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public UsuarioResponseDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }

    public MarcaResponseDto getMarca() {
        return marca;
    }

    public void setMarca(MarcaResponseDto marca) {
        this.marca = marca;
    }
}
