package com.toomate.backend.mapper.rotina;

import com.toomate.backend.dto.rotina.DiaSemanaRequestDto;
import com.toomate.backend.enums.Dia;
import com.toomate.backend.model.DiaSemana;

public class DIaSemanaMapper {
    public static DiaSemana toEntity(DiaSemanaRequestDto request){
        if(request == null){
            return null;
        }

        DiaSemana diaSemana = new DiaSemana();

        diaSemana.setNomeDia(request.getNomeDia().getDesc());

        return diaSemana;
    }

    public static DiaSemana toEntity(Dia request){
        if(request == null){
            return null;
        }

        DiaSemana diaSemana = new DiaSemana();

        diaSemana.setNomeDia(request.getDesc());

        return diaSemana;
    }
}
