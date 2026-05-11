package com.toomate.backend.mapper.lote;

import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.mapper.marca.MarcaMapper;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Lote;
import com.toomate.backend.model.Marca;
import com.toomate.backend.model.Usuario;

import java.util.List;

public class LoteMapper {
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
        dto.setMarca(MarcaMapper.toDto(lote.getMarca()));

        return dto;
    }

    public static List<LoteResponseDto> toDto(List<Lote> entity) {
        return entity.stream().map(lote -> new LoteResponseDto(lote.getIdLote(), lote.getDataValidade(), lote.getPrecoUnitario(), lote.getQuantidadeMedida(), lote.getDataEntrada(), UsuarioMapper.toResponse(lote.getUsuario()), MarcaMapper.toDto(lote.getMarca()))).toList();
    }
}
