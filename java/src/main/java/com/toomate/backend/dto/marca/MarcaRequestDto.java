package com.toomate.backend.dto.marca;

import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class MarcaRequestDto {
    @NotBlank(message = "O nome não pode ser vazio.")
    @Schema(description = "nome da marca", example = "camil")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "idInsumo")
//    @Schema(description = "Insumo do qual a marca pertence")
//    private Insumo insumo;
//    @ManyToOne
//    @JoinColumn(name = "idFornecedor")
//    @Schema(description = "Fornecedor do qual a marca pertence")
//    private Fornecedor fornecedor;
    @Schema(description = "Fk do insumo do qual a marca pertence", example = "1")
    private Integer fkInsumo;

    public MarcaRequestDto(String nome, Integer fkInsumo, Integer fkFornecedor) {
        this.nome = nome;
        this.fkInsumo = fkInsumo;
        this.fkFornecedor = fkFornecedor;
    }

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

    @Schema(description = "Fk do fornecedor do qual a marca pertence", example = "1")
    private Integer fkFornecedor;


}
