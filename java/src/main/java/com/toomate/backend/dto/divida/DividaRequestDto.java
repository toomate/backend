package com.toomate.backend.dto.divida;

import jakarta.persistence.JoinColumn;
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

    @Null
    private LocalDate dataPagamento;
    private Boolean pago;

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public String getPedido() {
        return pedido;
    }

    public Boolean getPago() {
        return pago;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    @NotBlank
    private Integer clienteId;
}
