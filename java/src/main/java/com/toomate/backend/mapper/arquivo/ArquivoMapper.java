package com.toomate.backend.mapper.arquivo;

import com.toomate.backend.dto.arquivo.ArquivoRequestDto;
import com.toomate.backend.model.Arquivo;

import java.time.LocalDate;

public class ArquivoMapper {
    public static Arquivo toEntity(ArquivoRequestDto request) {
        if (request == null) {
            return null;
        }

        Arquivo arquivo = new Arquivo();

        arquivo.setNomeOriginal(request.getNomeOriginal());
        arquivo.setNomeBucket(request.getNomeBucket());
        arquivo.setChave(request.getChave());
        arquivo.setDtCriacao(LocalDate.now());

        return arquivo;
    }
}
