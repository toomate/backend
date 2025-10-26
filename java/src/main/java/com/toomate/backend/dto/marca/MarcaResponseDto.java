package com.toomate.backend.dto.marca;

import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class MarcaResponseDto {
    @Schema(description = "Id númerico da marca(incrementa automaticamente)", example = "1")
    private Integer idMarca;
    @Schema(description = "nome da marca", example = "camil")
    private String nome;
    @Schema(description = "Insumo do qual a marca pertence")
    private InsumoResponseDto insumo;
    @Schema(description = "Insumo do qual a marca pertence")
    private FornecedorResponseDto fornecedor;

    public MarcaResponseDto() {
    }

    public MarcaResponseDto(Integer idMarca, String nome, InsumoResponseDto insumo, FornecedorResponseDto fornecedor) {
        this.idMarca = idMarca;
        this.nome = nome;
        this.insumo = insumo;
        this.fornecedor = fornecedor;
    }

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
