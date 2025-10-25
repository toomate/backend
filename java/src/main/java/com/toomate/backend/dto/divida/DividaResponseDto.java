package com.toomate.backend.dto.divida;

import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.model.Cliente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class DividaResponseDto {
    private Integer idDivida;
    private Double valor;
    private LocalDate dataCompra;
    private LocalDate dataPagamento;
    private String pedido;
    private Boolean pago;
    private ClientesResponseDto cliente;

    public DividaResponseDto(Integer idDivida, Double valor, LocalDate dataCompra, LocalDate dataPagamento, String pedido, Boolean pago, ClientesResponseDto cliente) {
        this.idDivida = idDivida;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataPagamento = dataPagamento;
        this.pedido = pedido;
        this.pago = pago;
        this.cliente = cliente;
    }

    public Integer getIdDivida() {
        return idDivida;
    }

    public void setIdDivida(Integer idDivida) {
        this.idDivida = idDivida;
    }

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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public ClientesResponseDto getCliente() {
        return cliente;
    }

    public void setCliente(ClientesResponseDto cliente) {
        this.cliente = cliente;
    }
}
