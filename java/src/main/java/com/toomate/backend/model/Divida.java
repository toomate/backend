package com.toomate.backend.model;

import io.swagger.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@Schema(description = "Representa uma divida de um cliente.")
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico da divida(incrementa automaticamente)", example = "1")
    private Integer idDivida;
    @Schema(description = "Valor da dívida do cliente", example = "R$ 20,99")
    private Double valor;
    @Schema(description = "Data em que a dívida foi feita", example = "03/10/2025")
    private LocalDate dataCompra;
    @Schema(description = "Data em que a dívida foi paga", example = "03/10/2025")
    private LocalDate dataPagamento;
    @Schema(description = "Descrição do que foi o pedido", example = "true ou false")
    private String pedido;
    @Schema(description = "Booleano que sinaliza se está pago ou não", example = "Frango assado")
    private Boolean pago;
    @Schema(description = "Id númerico do cliente(incrementa automaticamente)", example = "1")
    private Integer clienteId;

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
