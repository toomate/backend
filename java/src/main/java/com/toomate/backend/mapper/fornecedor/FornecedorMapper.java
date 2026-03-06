package com.toomate.backend.mapper.fornecedor;

import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.model.Fornecedor;

import java.util.List;

public final class FornecedorMapper {
    private FornecedorMapper() {
    }

    public static FornecedorResponseDto toResponse(Fornecedor entity) {
        if (entity == null) {
            return null;
        }

        FornecedorResponseDto dto = new FornecedorResponseDto();
        dto.setIdFornecedor(entity.getId());
        dto.setLink(entity.getLink());
        dto.setRazaoSocial(entity.getRazaoSocial());
        dto.setTelefone(entity.getTelefone());
        return dto;
    }

    public static List<FornecedorResponseDto> toResponseList(List<Fornecedor> fornecedores) {
        return fornecedores.stream().map(FornecedorMapper::toResponse).toList();
    }
}
