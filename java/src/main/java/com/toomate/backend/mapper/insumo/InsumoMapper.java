package com.toomate.backend.mapper.insumo;

import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.dto.insumo.InsumoNotificationDto;
import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;

import java.util.List;

public class InsumoMapper {
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
            categoriaDto.setRotatividade(categoria.getRotatividade());
            dto.setCategoria(categoriaDto);
        }

        return dto;
    }

    public static List<InsumoResponseDto> toDto(List<Insumo> entity) {
        return entity.stream().map(InsumoMapper::toDto).toList();
    }

    public static InsumoNotificationDto toNotification(Insumo insumo, Double atual) {
        if (insumo == null) {
            return null;
        }
        InsumoNotificationDto dto = new InsumoNotificationDto(insumo, atual);
        return dto;
    }
}
