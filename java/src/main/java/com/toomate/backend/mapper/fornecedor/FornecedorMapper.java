package com.toomate.backend.mapper.fornecedor;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Usuario;

public class FornecedorMapper {
    public static Fornecedor toEntity(FornecedorRequestDto fornecedorRequestDto){
        if (fornecedorRequestDto == null){
            return null;
        }

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setLink(fornecedorRequestDto.getLink());
        fornecedor.setRazaoSocial(fornecedorRequestDto.getRazaoSocial());
        fornecedor.setTelefone(fornecedorRequestDto.getTelefone());

        return fornecedor;
    }

    public static FornecedorResponseDto toResponse(Fornecedor entity){
        if (entity == null){
            return null;
        }

        FornecedorResponseDto dto = new FornecedorResponseDto();

        dto .setIdFornecedor(entity.getId());
        dto .setLinkWhatsapp(entity.getLink());
        dto .setRazaoSocial(entity.getRazaoSocial());
        dto .setTelefone(entity.getTelefone());

        return dto;
    }
}
