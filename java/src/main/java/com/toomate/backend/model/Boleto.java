package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Schema(description = "Representa um boleto que o restaurante tem que pegar")
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "1")
    private Integer idBoleto;
    @Schema(description = "Descrição do boleto", example = "Boleto do fornecedor de arroz")
    private String descricao;
    @Schema(description = "Categoria do boleto", example = "fornecedor")
    private String categoria;
    @Schema(description = "Status de pagamento do boleto", example = "true")
    private Boolean pago;
    @Schema(description = "Data de vencimento do boleto", example = "2025-10-03")
    private LocalDate dataVencimento;
    @Schema(description = "Data de pagamento do boleto", example = "2025-10-03")
    private LocalDate dataPagamento;
    @Schema(description = "Valor do boleto", example = "R$ 20,99")
    private Double valor;
    @Schema(description = "Id númerico do fornecedor", example = "1")
    private Integer idFornecedor;

    public Boleto(Integer idBoleto, String descricao, String categoria, Boolean pago, LocalDate dataVencimento, LocalDate dataPagamento, Double valor, Integer idFornecedor) {
        this.idBoleto = idBoleto;
        this.descricao = descricao;
        this.categoria = categoria;
        this.pago = pago;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.idFornecedor = idFornecedor;
    }

    public Boleto() {
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
