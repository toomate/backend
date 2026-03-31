package com.toomate.backend.dto.rotina;

import com.toomate.backend.model.Rotina;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageRotinaResponseDto(
        List<Rotina> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas
) {
    public static PageRotinaResponseDto de(Page<Rotina> rotinaPage){
        return new PageRotinaResponseDto(
                rotinaPage.getContent(),
                rotinaPage.getNumber(),
                rotinaPage.getSize(),
                rotinaPage.getTotalElements(),
                rotinaPage.getTotalPages()
        );
    }
}