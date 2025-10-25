package com.toomate.backend.mapper.Divida;

import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.dto.divida.DividaRequestDto;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.cliente.ClienteMapper;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.model.Divida;
import com.toomate.backend.dto.divida.*;

import java.util.Objects;

public class DividaMapper {
    public static Divida toEntity(DividaRequestDto dto, Cliente cliente){
        if (Objects.isNull(dto)){
            throw new EntradaInvalidaException("Divida invalida");
        }

        return new Divida(
                dto.getValor(),
                dto.getDataCompra(),
                dto.getDataPagamento(),
                dto.getPedido(),
                dto.getPago(),
                cliente
        );
    }

    public static DividaResponseModalDto toResponsesModal(Divida entity){
        if (Objects.isNull(entity)){
            throw new EntradaInvalidaException("Divida invalida");
        }

        return new DividaResponseModalDto(
                entity.getCliente().getIdCliente(),
                entity.getValor(),
                entity.getDataCompra(),
                entity.getPedido()
        );
    }

    public static DividaResponseDto toResponse(Divida entity, Cliente cliente){
        if (Objects.isNull(entity) || Objects.isNull(cliente)){
            throw new EntradaInvalidaException("Divida e/ou cliente inválidos!");
        }

        ClientesResponseDto clienteResponse = ClienteMapper.toResponses(cliente);

        return new DividaResponseDto(
                entity.getIdDivida(),
                entity.getValor(),
                entity.getDataCompra(),
                entity.getDataPagamento(),
                entity.getPedido(),
                entity.getPago(),
                clienteResponse
        );
    }
}
