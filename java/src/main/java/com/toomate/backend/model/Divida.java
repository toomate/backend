package com.toomate.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDivida;

    private Double valor;
    private LocalDate dataCompra;
    private LocalDate dataPagamento;
    private String pedido;
    private Boolean pago;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente cliente;



    public Divida(Double valor, LocalDate dataCompra, LocalDate dataPagamento, String pedido, Boolean pago, Cliente cliente) {
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataPagamento = dataPagamento;
        this.pedido = pedido;
        this.pago = pago;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Divida() {
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
