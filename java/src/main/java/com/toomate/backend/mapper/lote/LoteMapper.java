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
        entity.setAtivo(dto.getAtivo());
        entity.setPrecoUnitario(dto.getPrecoUnitario());
        entity.setQuantidadeMedida(dto.getQuantidadeMedida());
        entity.setUsuario(usuario);
        entity.setMarca(marca);
        entity.setUnidadeMedida(dto.getUnidadeMedida());
        entity.setQuantidadeOriginal(dto.getQuantidadeOriginal());
        entity.setQuantidadeAtual(dto.getQuantidadeAtual());
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
        dto.setAtivo(lote.getAtivo());
        dto.setPrecoUnitario(lote.getPrecoUnitario());
        dto.setQuantidadeMedida(lote.getQuantidadeMedida());
        dto.setUsuario(UsuarioMapper.toResponse(lote.getUsuario()));
        dto.setMarca(MarcaMapper.toDto(lote.getMarca()));
        dto.setUnidadeMedida(lote.getUnidadeMedida());
        dto.setQuantidadeAtual(lote.getQuantidadeAtual());
        dto.setQuantidadeOriginal(lote.getQuantidadeOriginal());
        return dto;
    }

    public static List<LoteResponseDto> toDto(List<Lote> entity) {
        return entity.stream().map(LoteMapper::toDto).toList();
    }
}
