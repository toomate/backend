package com.toomate.backend.mapper.rotina;

import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Rotina;

public class RotinaMapper {
    public static Rotina toEntity(RotinaRequestDto request){
        if (request == null){
            return null;
        }

        Rotina rotina = new Rotina();

        rotina.setTitulo(request.getTitulo());
        rotina.setQuantidadeMedida(request.getQuantidadeMedida());

        return rotina;
    }
}
