package com.toomate.backend.dto.lote;

import com.toomate.backend.dto.marca.MarcaResponseDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class LoteResponseDto {
    @Schema(description = "Id númerico do lote(incrementa automaticamente)", example = "1")
    private Integer idLote;
    @Schema(description = "data de validade)", example = "2025-10-19")
    private LocalDate dataValidade;
    @Schema(description = "preço unitário)", example = "25.99")
    private Double precoUnitario;
    @Schema(description = "quantidade da medida)", example = "5")
    private Double quantidadeMedida;
    @Schema(description = "data de entrada)", example = "2025-10-19")
    private LocalDate dataEntrada;
    @Schema(description = "Marca dos insumos do lote")
    private UsuarioResponseDto usuario;
    @Schema(description = "Usuario que cadastrou o lote")
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
