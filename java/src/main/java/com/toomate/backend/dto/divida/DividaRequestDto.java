package com.toomate.backend.dto.divida;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class DividaRequestDto {

    @Positive
    @NotNull
    @Schema(description = "Valor da divida", example = "20.99")
    private Double valor;

    @PastOrPresent
    @NotNull
    @Schema(description = "Data da divida", example = "2025-10-25")
    private LocalDate dataCompra;

    @NotBlank
    @Schema(description = "Pedido da divida", example = "feijoada")
    private String pedido;
    @NotNull
    @Schema(description = "id do cliente que tem a divida", example = "1")
    private Integer idCliente;
    @Schema(description = "Data de pagamento da divida", example = "2025-10-25")
    private LocalDate dataPagamento;
    @Schema(description = "Status de pagamento da divida", example = "true")
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
