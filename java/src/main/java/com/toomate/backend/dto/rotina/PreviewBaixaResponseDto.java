package com.toomate.backend.dto.rotina;

import java.util.List;

public class PreviewBaixaResponseDto {
    private Integer rotinaId;
    private String titulo;
    private List<ItemBaixaResponseDto> itens;

    public Integer getRotinaId() {
        return rotinaId;
    }

    public void setRotinaId(Integer rotinaId) {
        this.rotinaId = rotinaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<ItemBaixaResponseDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemBaixaResponseDto> itens) {
        this.itens = itens;
    }
}
