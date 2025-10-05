package com.toomate.backend.dto.marca;

import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;

public class MarcaResponseDto {
    private Integer idMarca;
    private String nome;
    private InsumoResponseDto insumo;
    private FornecedorResponseDto fornecedor;

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InsumoResponseDto getInsumo() {
        return insumo;
    }

    public void setInsumo(InsumoResponseDto insumo) {
        this.insumo = insumo;
    }

    public FornecedorResponseDto getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorResponseDto fornecedor) {
        this.fornecedor = fornecedor;
    }
}
