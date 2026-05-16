package com.toomate.backend.dto.estoque_grupo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EstoqueGrupo {
    private Integer fkCategoria;
    private Integer fkInsumo;
    private String categoria;
    private String insumo;
    private Integer qtdMinima;
    private Double qtdTotal;
    private Double qtdAtual;
    private String medida;
    private LocalDate dtVencimento;
    private List<InsumoAgrupado> itens;

    public void calcularQtdTotal() {
        Double qtd = 0.0;
        for (InsumoAgrupado atual : itens) {
            qtd += atual.getQuantidadeMedida();
        }
        this.qtdTotal = qtd;
    }

    public void calcularQtdAtual() {
        Double qtd = 0.0;
        for (InsumoAgrupado atual : itens) {
            qtd += atual.getQuantidadeTotal();
        }
        this.qtdAtual = qtd;
    }

    public void calcularMenorData() {
        LocalDate dtAtual = LocalDate.now();
        Optional<InsumoAgrupado> maisProxima = itens.stream()
                .min(Comparator.comparingLong(
                        item -> Math.abs(ChronoUnit.DAYS.between(item.getDataValidade(), dtAtual))
                ));

        if (maisProxima.isPresent()) {
            dtVencimento = maisProxima.get().getDataValidade();
        }
    }

    public EstoqueGrupo(Integer fkCategoria, Integer fkInsumo, String categoria, String insumo, Integer qtdMinima, Double qtdTotal, Double qtdAtual, String medida, LocalDate dtVencimento, List<InsumoAgrupado> itens) {
        this.fkCategoria = fkCategoria;
        this.fkInsumo = fkInsumo;
        this.categoria = categoria;
        this.insumo = insumo;
        this.qtdMinima = qtdMinima;
        this.qtdTotal = qtdTotal;
        this.qtdAtual = qtdAtual;
        this.medida = medida;
        this.dtVencimento = dtVencimento;
        this.itens = itens;
    }

    public EstoqueGrupo(){}

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public Integer getFkInsumo() {
        return fkInsumo;
    }

    public void setFkInsumo(Integer fkInsumo) {
        this.fkInsumo = fkInsumo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public Integer getQtdMinima() {
        return qtdMinima;
    }

    public void setQtdMinima(Integer qtdMinima) {
        this.qtdMinima = qtdMinima;
    }

    public Double getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(Double qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public Double getQtdAtual() {
        return qtdAtual;
    }

    public void setQtdAtual(Double qtdAtual) {
        this.qtdAtual = qtdAtual;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public List<InsumoAgrupado> getItens() {
        return itens;
    }

    public void setItens(List<InsumoAgrupado> itens) {
        this.itens = itens;
    }
}
