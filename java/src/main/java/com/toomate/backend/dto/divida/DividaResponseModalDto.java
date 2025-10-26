package com.toomate.backend.dto.divida;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class DividaResponseModalDto {
    @Schema(description = "Id do cliente", example = "1")
    private Integer idCliente;
    @Schema(description = "Valor da dívida", example = "20.99")
    private Double valor;
    @Schema(description = "Data da divida", example = "2025-10-25")
    private LocalDate dataCompra;
    @Schema(description = "Pedido da divida", example = "feijoada")
    private String pedido;

    public DividaResponseModalDto(Integer idCliente, Double valor, LocalDate dataCompra, String pedido) {
        this.idCliente = idCliente;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.pedido = pedido;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public String getPedido() {
        return pedido;
    }
}
