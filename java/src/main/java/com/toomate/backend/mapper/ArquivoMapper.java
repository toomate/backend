package com.toomate.backend.mapper;

import com.toomate.backend.dto.arquivo.ArquivoRequestDto;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.model.Boleto;

import java.time.LocalDate;

public class ArquivoMapper {
    public static Arquivo toEntity(ArquivoRequestDto request) {
        if (request == null) {
            return null;
        }

        Arquivo arquivo = new Arquivo();

        arquivo.setNomeUsuario(request.getNomeUsuario());
        arquivo.setNomeOriginal(request.getNomeOriginal());
        arquivo.setNomeBucket(request.getNomeBucket());
        arquivo.setChave(request.getChave());
        arquivo.setDtCriacao(LocalDate.now());
        arquivo.setComprovante(request.getIdBoleto());

        return arquivo;
    }
}
