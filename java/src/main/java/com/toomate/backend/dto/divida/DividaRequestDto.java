package com.toomate.backend.dto.divida;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class DividaRequestDto {

    @Positive
    @NotNull
    private Double valor;

    @PastOrPresent
    @NotNull
    private LocalDate dataCompra;

    @NotBlank
    private String pedido;

    @NotNull
    private Integer idCliente;

    private LocalDate dataPagamento;
    private Boolean pago;


    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }
}
