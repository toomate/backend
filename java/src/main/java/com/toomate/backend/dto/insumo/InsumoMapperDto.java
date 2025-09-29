package com.toomate.backend.dto.insumo;

import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;

import java.util.List;

public class InsumoMapperDto {
    public static Insumo toEntity(InsumoRequestDto dto, Categoria categoria) {
        if (dto == null) {
            return null;
        }

        Insumo entity = new Insumo();
        entity.setNome(dto.getNome());
        entity.setCategoria(categoria);
        entity.setQtdMinima(dto.getQtdMinima());
        entity.setUnidadeMedida(dto.getUnidadeMedida());

        return entity;
    }

    public static InsumoResponseDto toDto(Insumo entity) {
        if (entity == null) {
            return null;
        }

        InsumoResponseDto dto = new InsumoResponseDto();
        dto.setIdInsumo(entity.getIdInsumo());
        dto.setNome(entity.getNome());
        dto.setQtdMinima(entity.getQtdMinima());
        dto.setUnidadeMedida(entity.getUnidadeMedida());

        Categoria categoria = entity.getCategoria();
        if (categoria != null) {
            CategoriaResponseDto categoriaDto = new CategoriaResponseDto();
            categoriaDto.setIdCategoria(categoria.getIdCategoria());
            categoriaDto.setNome(categoria.getNome());
            dto.setCategoria(categoriaDto);
        }

        return dto;
    }

    public static List<InsumoResponseDto> toDto(List<Insumo> entity) {
        return entity.stream().map(InsumoMapperDto::toDto).toList();
    }
}
