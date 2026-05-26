package com.toomate.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class HistoricoLote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorico;
    @ManyToOne
    @JoinColumn(name = "fkLote", referencedColumnName = "idLote")
    private Lote lote;
    private Integer quantidadeRetirada;
    private LocalDateTime dataHoraAlteracao;

    public HistoricoLote(Integer idHistorico, Lote lote, Integer quantidadeRetirada, LocalDateTime dataHoraAlteracao) {
        this.idHistorico = idHistorico;
        this.lote = lote;
        this.quantidadeRetirada = quantidadeRetirada;
        this.dataHoraAlteracao = dataHoraAlteracao;
    }

    public HistoricoLote() {
    }

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
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
