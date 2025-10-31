package com.toomate.backend.dto.rotina;

import com.toomate.backend.enums.Dia;
import com.toomate.backend.model.Insumo;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class RotinaRequestDto {
    @NotNull
    private Integer idInsumo;
    @NotNull
    private Integer quantidadeMinima;
    @NotNull
    private LocalTime periodo;
    @NotNull
    private Dia diaSemana;

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public LocalTime getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalTime periodo) {
        this.periodo = periodo;
    }

    public Dia getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Dia diaSemana) {
        this.diaSemana = diaSemana;
    }
}
