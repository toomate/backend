package com.toomate.backend.dto.rotina;

import com.toomate.backend.model.Rotina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public record PageResponseDto(
        List<Rotina> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas
) {
    public static PageResponseDto de(Page<Rotina> rotinaPage){
        return new PageResponseDto(
                rotinaPage.getContent(),
                rotinaPage.getNumber(),
                rotinaPage.getSize(),
                rotinaPage.getTotalElements(),
                rotinaPage.getTotalPages()
        );
    }
}