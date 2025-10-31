package com.toomate.backend.dto.rotina;

import com.toomate.backend.enums.Dia;
import jakarta.validation.constraints.NotNull;

public class DiaSemanaRequestDto {
    @NotNull
    private Dia nomeDia;

    public Dia getNomeDia() {
        return nomeDia;
    }

    public void setNomeDia(Dia nomeDia) {
        this.nomeDia = nomeDia;
    }


}
