package com.toomate.backend.dto.divida;

import com.toomate.backend.dto.rotina.PageRotinaResponseDto;
import com.toomate.backend.model.Divida;
import com.toomate.backend.model.Rotina;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageDividaResponseDto (
        List<Divida> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas
) {
    public static PageDividaResponseDto de(Page<Divida> rotinaPage){
        return new PageDividaResponseDto(
                rotinaPage.getContent(),
                rotinaPage.getNumber(),
                rotinaPage.getSize(),
                rotinaPage.getTotalElements(),
                rotinaPage.getTotalPages()
        );
    }
}
