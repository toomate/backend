package com.toomate.backend.dto.marca;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;
    private Integer fkInsumo;
    private Integer fkFornecedor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFkInsumo() {
        return fkInsumo;
    }

    public void setFkInsumo(Integer fkInsumo) {
        this.fkInsumo = fkInsumo;
    }

    public Integer getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(Integer fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }
}
