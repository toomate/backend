package com.toomate.backend.dto.categoria;

import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.model.Categoria;

import java.util.List;

public class CategoriaMapperDto {
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
        return entity.stream().map(CategoriaMapperDto::toDto).toList();
    }
}
