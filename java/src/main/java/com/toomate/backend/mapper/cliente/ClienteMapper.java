package com.toomate.backend.mapper.cliente;

import com.toomate.backend.dto.cliente.ClienteRequestDto;
import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Cliente;

import java.util.Objects;

public class ClienteMapper {
    public static ClientesResponseDto toResponses(Cliente entity){
        if(Objects.isNull(entity)){
            throw new EntradaInvalidaException("cliente nulo");
        }
        return new ClientesResponseDto(
                entity.getIdCliente(),
                entity.getNome(),
                entity.getTelefone()
        );
    }

    public static Cliente toEntity(ClienteRequestDto dto){
        if (Objects.isNull(dto)){
            throw new EntradaInvalidaException("Cliente nulo");
        }
        return new Cliente(
                dto.getNome(),
                dto.getTelefone(),
                dto.getCep(),
                dto.getLogradouro(),
                dto.getBairro()
        );
    }
}
