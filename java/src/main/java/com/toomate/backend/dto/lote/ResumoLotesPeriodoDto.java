package com.toomate.backend.dto.lote;

public class ResumoLotesPeriodoDto {
    private Double totalValor;
    private Long totalRegistros;

    public ResumoLotesPeriodoDto() {
    }

    public ResumoLotesPeriodoDto(Double totalValor, Long totalRegistros) {
        this.totalValor = totalValor;
        this.totalRegistros = totalRegistros;
    }

    public Double getTotalValor() {
        return totalValor;
    }

    public void setTotalValor(Double totalValor) {
        this.totalValor = totalValor;
    }

    public Long getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
