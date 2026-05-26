package com.toomate.backend.mapper.historico_lote;

import com.toomate.backend.dto.historico_lote.HistoricoLoteResponseDto;
import com.toomate.backend.model.HistoricoLote;

import java.util.List;

public class HistoricoLoteMapper {
    public static HistoricoLoteResponseDto toResponse(HistoricoLote entity) {
        if (entity == null) {
            return null;
        }

        HistoricoLoteResponseDto response = new HistoricoLoteResponseDto();

        response.setIdHistorico(entity.getIdHistorico());
        response.setQuantidadeRetirada(entity.getQuantidadeRetirada());
        response.setFkLote(entity.getLote().getIdLote());

        return response;
    }

    public static List<HistoricoLoteResponseDto> toResponse(List<HistoricoLote> entities){
        return entities.stream().map(HistoricoLoteMapper::toResponse).toList();
    }
}
