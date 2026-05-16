package com.toomate.backend.dto.rotina;

import com.toomate.backend.model.Insumo;

import java.util.List;
import java.util.Map;

public class RotinaResponseDto {
    private Integer id;
    private String titulo;
    private List<InsumoRotinaResponseDto> insumos;

    public RotinaResponseDto(Integer id, String titulo, List<InsumoRotinaResponseDto> insumos) {
        this.id = id;
        this.titulo = titulo;
        this.insumos = insumos;
    }

    public RotinaResponseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<InsumoRotinaResponseDto> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<InsumoRotinaResponseDto> insumos) {
        this.insumos = insumos;
    }
}
