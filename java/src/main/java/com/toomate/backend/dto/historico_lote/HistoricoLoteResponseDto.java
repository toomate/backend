package com.toomate.backend.dto.historico_lote;

import com.toomate.backend.model.Lote;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class HistoricoLoteResponseDto {
    private Integer idHistorico;
    private Integer fkLote;
    private Integer quantidadeRetirada;
    private LocalDateTime dataHoraAlteracao;


    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Integer getFkLote() {
        return fkLote;
    }

    public void setFkLote(Integer fkLote) {
        this.fkLote = fkLote;
    }

    public Integer getQuantidadeRetirada() {
        return quantidadeRetirada;
    }

    public void setQuantidadeRetirada(Integer quantidadeRetirada) {
        this.quantidadeRetirada = quantidadeRetirada;
    }

    public LocalDateTime getDataHoraAlteracao() {
        return dataHoraAlteracao;
    }

    public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
        this.dataHoraAlteracao = dataHoraAlteracao;
    }
}
