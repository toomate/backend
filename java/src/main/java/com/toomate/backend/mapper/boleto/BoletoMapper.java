package com.toomate.backend.mapper.boleto;

import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.model.Boleto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BoletoMapper {

    public static Boleto toEntity (BoletoRequestDto request) {
        Boleto boleto = new Boleto();
        boleto.setDescricao(request.getDescricao());
        boleto.setCategoria(request.getCategoria());
        boleto.setPago(request.getPago());
        boleto.setDataVencimento(request.getDataVencimento());
        boleto.setDataPagamento(request.getDataPagamento());
        boleto.setValor(request.getValor());
        boleto.setIdFornecedor(request.getIdFornecedor());

        return boleto;
    }

}
