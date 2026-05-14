package com.toomate.backend.mapper.categoria;

import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;

import java.util.List;

public class CategoriaMapper {
    // Converte DTO para Entidade
    public static Categoria toEntity(CategoriaRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Categoria entity = new Categoria();
        entity.setNome(dto.getNome());

        return entity;
    }

    // Converte Entidade para DTO (resposta)
    public static CategoriaResponseDto toDto(Categoria entity) {
        if (entity == null) {
            return null;
        }

        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setIdCategoria(entity.getIdCategoria());
        dto.setNome(entity.getNome());

        return dto;
    }

    public static List<CategoriaResponseDto> toResponseDto(List<Categoria> entity) {
        return entity.stream().map(CategoriaMapper::toDto).toList();
    }
}
