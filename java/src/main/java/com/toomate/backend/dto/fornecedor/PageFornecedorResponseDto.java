package com.toomate.backend.dto.fornecedor;

import com.toomate.backend.dto.rotina.PageRotinaResponseDto;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Rotina;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageFornecedorResponseDto(
        List<Fornecedor> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas
) {
    public static PageFornecedorResponseDto de(Page<Fornecedor> fornecedorPage){
        return new PageFornecedorResponseDto(
                fornecedorPage.getContent(),
                fornecedorPage.getNumber(),
                fornecedorPage.getSize(),
                fornecedorPage.getTotalElements(),
                fornecedorPage.getTotalPages()
        );
    }
}
