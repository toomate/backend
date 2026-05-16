package com.toomate.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rotinaInsumo")
public class RotinaInsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idRotina")
    @JsonIgnore
    private Rotina rotina;
    @ManyToOne
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;
    private Integer quantidadeInsumo;

    public Integer getQuantidadeInsumo() {
        return quantidadeInsumo;
    }

    public void setQuantidadeInsumo(Integer quantidadeInsumo) {
        this.quantidadeInsumo = quantidadeInsumo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }
}
