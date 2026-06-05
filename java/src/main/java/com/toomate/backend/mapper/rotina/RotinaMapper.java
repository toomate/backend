package com.toomate.backend.mapper.rotina;

import com.toomate.backend.dto.rotina.InsumoRotinaResponseDto;
import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.dto.rotina.RotinaResponseDto;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Rotina;
import com.toomate.backend.model.RotinaInsumo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotinaMapper {
    public static Rotina toEntity(RotinaRequestDto request) {
        if (request == null) {
            return null;
        }

        Rotina rotina = new Rotina();

        rotina.setTitulo(request.getTitulo());

        return rotina;
    }

    public static RotinaResponseDto toResponse(Rotina request) {
        if (request == null) {
            return null;
        }

        RotinaResponseDto response = new RotinaResponseDto();
        List<InsumoRotinaResponseDto> insumos =
                request.getRotinaInsumos()
                        .stream()
                        .map(ri -> new InsumoRotinaResponseDto(
                                ri.getInsumo().getIdInsumo(),
                                ri.getInsumo().getNome(),
                                ri.getQuantidadeInsumo(),
                                ri.getUnidadeMedida()
                        ))
                        .toList();

        response.setId(request.getId());
        response.setTitulo(request.getTitulo());
        response.setInsumos(insumos);

        return response;
    }

    public static List<RotinaResponseDto> toResponse(List<Rotina> request) {
        return request.stream().map(RotinaMapper::toResponse).toList();
    }
}
