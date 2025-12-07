package com.toomate.backend.dto.marca;

import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import com.toomate.backend.mapper.fornecedor.FornecedorMapper;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Marca;

import java.util.List;

public class MarcaMapperDto {
    public static Marca toEntity(MarcaRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Marca entity = new Marca();
        entity.setNomeMarca(dto.getNome());

        return entity;
    }

    public static Marca toEntity(MarcaRequestDto dto, Insumo insumo, Fornecedor fornecedor) {
        if (dto == null) {
            return null;
        }

        Marca entity = new Marca();
        entity.setNomeMarca(dto.getNome());
        entity.setFornecedor(fornecedor);
        entity.setInsumo(insumo);

        return entity;
    }

    public static MarcaResponseDto toDto (Marca marca) {
        if (marca == null) {
            return null;
        }

        MarcaResponseDto dto = new MarcaResponseDto();
        dto.setIdMarca(marca.getIdMarca());
        dto.setNome(marca.getNomeMarca());
        dto.setInsumo(InsumoMapperDto.toDto(marca.getInsumo()));
        dto.setFornecedor(FornecedorMapper.toResponse(marca.getFornecedor()));
        return dto;
    }

    public static List<MarcaResponseDto> toDto(List<Marca> entity) {
        return entity.stream().map(marca -> new MarcaResponseDto(marca.getIdMarca(), marca.getNomeMarca(), InsumoMapperDto.toDto(marca.getInsumo()), FornecedorMapper.toResponse(marca.getFornecedor()))).toList();
    }

}
