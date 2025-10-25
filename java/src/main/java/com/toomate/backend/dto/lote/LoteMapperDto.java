package com.toomate.backend.dto.lote;

import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaResponseDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Lote;
import com.toomate.backend.model.Marca;
import com.toomate.backend.model.Usuario;

import java.util.List;

public class LoteMapperDto {
    public static Lote toEntity(LoteRequestDto dto, Usuario usuario, Marca marca) {
        if (dto == null) {
            return null;
        }

        Lote entity = new Lote();
        entity.setDataEntrada(dto.getDataEntrada());
        entity.setDataValidade(dto.getDataValidade());
        entity.setPrecoUnitario(dto.getPrecoUnitario());
        entity.setQuantidadeMedida(dto.getQuantidadeMedida());
        entity.setUsuario(usuario);
        entity.setMarca(marca);

        return entity;
    }

    public static LoteResponseDto toDto (Lote lote) {
        if (lote == null) {
            return null;
        }

        LoteResponseDto dto = new LoteResponseDto();
        dto.setIdLote(lote.getIdLote());
        dto.setDataEntrada(lote.getDataEntrada());
        dto.setDataValidade(lote.getDataValidade());
        dto.setPrecoUnitario(lote.getPrecoUnitario());
        dto.setQuantidadeMedida(lote.getQuantidadeMedida());
        dto.setUsuario(UsuarioMapper.toResponse(lote.getUsuario()));
        dto.setMarca(MarcaMapperDto.toDto(lote.getMarca()));

        return dto;
    }

    public static List<LoteResponseDto> toDto(List<Lote> entity) {
        return entity.stream().map(lote -> new LoteResponseDto(lote.getIdLote(), lote.getDataValidade(), lote.getPrecoUnitario(), lote.getQuantidadeMedida(), lote.getDataEntrada(), UsuarioMapper.toResponse(lote.getUsuario()), MarcaMapperDto.toDto(lote.getMarca()))).toList();
    }
}
