package com.toomate.backend.categoria.interfaces.rest;

import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;

public final class CategoriaDtoMapper {
    private CategoriaDtoMapper() {
    }

    public static CategoriaResponseDto toResponse(CategoriaDomain categoria) {
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setIdCategoria(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setRotatividade(categoria.getRotatividade());
        return dto;
    }
}
