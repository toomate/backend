package com.toomate.backend.dto.arquivo_relacionamento;

import com.toomate.backend.enums.CategoriaComprovante;
import com.toomate.backend.enums.TipoEntidade;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class ArquivoRelacionamentoRequestDto { ;
    @NotNull
    private TipoEntidade tipoEntidade;
    @NotNull
    @DecimalMin(value = "1")
    private Integer idEntidade;
    private CategoriaComprovante categoria;

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(TipoEntidade tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public CategoriaComprovante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaComprovante categoria) {
        this.categoria = categoria;
    }

    public Integer getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Integer idEntidade) {
        this.idEntidade = idEntidade;
    }
}
