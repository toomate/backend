package com.toomate.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rotinaInsumo")
public class RotinaInsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "fkRotina", referencedColumnName = "idRotina")
    private Rotina rotina;
    @ManyToOne
    @JoinColumn(name = "fkInsumo", referencedColumnName = "idInsumo")
    private Insumo insumo;

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
