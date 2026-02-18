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
    private Double qtdTotal;
    private String medida;
    private LocalDate dtVencimento;
    private List<InsumoAgrupado> itens;

    public void calcularQtdTotal() {
        Double qtd = 0.0;
        for (InsumoAgrupado atual : itens) {
            qtd += atual.getQuantidadeMedida();
        }

        qtdTotal = qtd;
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

    public Double getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(Double qtdTotal) {
        this.qtdTotal = qtdTotal;
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
