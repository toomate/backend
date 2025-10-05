package com.toomate.backend.dto.marca;

import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Marca;

import java.util.List;

public class MarcaMapperDto {
    public static Marca toEntity(MarcaRequestDto dto, Insumo insumo, Fornecedor fornecedor) {
        if (dto == null) {
            return null;
        }

        Marca entity = new Marca();
        entity.setNomeMarca(dto.getNome());
        entity.setInsumo(insumo);
        entity.setFornecedor(fornecedor);

        return entity;
    }

    public static MarcaResponseDto toDto (Marca marca) {
        if (marca == null) {
            return null;
        }

        MarcaResponseDto dto = new MarcaResponseDto();
        dto.setIdMarca(marca.getIdMarca());
        dto.setNome(marca.getNomeMarca());

        Insumo insumo = marca.getInsumo();
        if (insumo != null) {
            InsumoResponseDto insumoDto = new InsumoResponseDto();
            insumoDto.setIdInsumo(insumo.getIdInsumo());
            dto.setInsumo(insumoDto);
        }

        Fornecedor fornecedor = marca.getFornecedor();
        if (fornecedor != null) {
            FornecedorResponseDto fornecedorDto = new FornecedorResponseDto();
            fornecedorDto.setIdFornecedor(fornecedor.getId());
            dto.setFornecedor(fornecedorDto);
        }

        return dto;
    }

    public static List<MarcaResponseDto> toDto(List<Marca> entity) {
        return entity.stream().map(MarcaMapperDto::toDto).toList();
    }

}
