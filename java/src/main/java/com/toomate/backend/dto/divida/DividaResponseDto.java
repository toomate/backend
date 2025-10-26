package com.toomate.backend.dto.divida;

import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.model.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class DividaResponseDto {
    @Schema(description = "Id da divida", example = "1")
    private Integer idDivida;
    @Schema(description = "Valor da divida", example = "20.99")
    private Double valor;
    @Schema(description = "Data da divida", example = "2025-10-25")
    private LocalDate dataCompra;
    @Schema(description = "Data de pagamento da divida", example = "2025-10-25")
    private LocalDate dataPagamento;
    @Schema(description = "Pedido da divida", example = "feijoada")
    private String pedido;
    @Schema(description = "Status de pagamento da divida", example = "true")
    private Boolean pago;
    @Schema(description = "Cliente que tem a dívida")
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
