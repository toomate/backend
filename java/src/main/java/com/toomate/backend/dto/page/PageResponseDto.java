package com.toomate.backend.dto.page;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseDto<T>{
        List<T> conteudo;
        int pagina;
        int tamanho;
        long total;
        long totalPaginas;

    public PageResponseDto() {
    }

    public PageResponseDto(List<T> conteudo, int pagina, int tamanho, long total, long totalPaginas) {
        this.conteudo = conteudo;
        this.pagina = pagina;
        this.tamanho = tamanho;
        this.total = total;
        this.totalPaginas = totalPaginas;
    }

    public PageResponseDto<T> de(Page<T> page){
        return new PageResponseDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public void setConteudo(List<T> conteudo) {
        this.conteudo = conteudo;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(long totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
}