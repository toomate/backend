package com.toomate.backend.dto.divida;

import java.time.LocalDate;

public class DividaResponseModalDto {
    private Integer idCliente;
    private Double valor;
    private LocalDate dataCompra;
    private String pedido;

    public DividaResponseModalDto(Integer idCliente, Double valor, LocalDate dataCompra, String pedido) {
        this.idCliente = idCliente;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.pedido = pedido;
    }
}
