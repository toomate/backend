package com.toomate.backend.mapper.rotina;

import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Rotina;

public class RotinaMapper {
    public static Rotina toEntity(RotinaRequestDto request, Insumo insumo){
        if (request == null){
            return null;
        }

        Rotina rotina = new Rotina();

        rotina.setInsumo(insumo);
        rotina.setPeriodo(request.getPeriodo());
        rotina.setQuantidadeMinima(request.getQuantidadeMinima());

        return rotina;
    }
}
