package com.toomate.backend.mapper.rotina;

import com.toomate.backend.model.DiaRotina;
import com.toomate.backend.model.DiaSemana;
import com.toomate.backend.model.Rotina;

public class DiaRotinaMapper {
    public static DiaRotina toEntity(Rotina rotina, DiaSemana diaSemana){
        if (rotina == null || diaSemana == null){
            return null;
        }

        DiaRotina diaRotina = new DiaRotina();

        diaRotina.setRotina(rotina);
        diaRotina.setDiaSemana(diaSemana);

        return diaRotina;
    }
}
