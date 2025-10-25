    package com.toomate.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Schema(description = "Representa um cliente do restaurante.")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id númerico do cliente(incrementa automaticamente)", example = "1")
    private Integer idCliente;
    @Schema(description = "Nome do cliente", example = "Lucas")
    private String nome;
    @Schema(description = "Telefone do cliente", example = "11987654321")
    private String telefone;
    @Schema(description = "Cep do cliente", example = "01001000")
    private String cep;
    @Schema(description = "Logradouro do cliente", example = "Rua das Flores")
    private String logradouro;
    @Schema(description = "Bairro do cliente", example = "Jardim Brasil")
    private String bairro;

    @OneToMany(mappedBy = "cliente")
    private List<Divida> dividas;

    public Cliente(String nome, String telefone, String cep, String logradouro, String bairro) {
        this.nome = nome;
        this.telefone = telefone;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        dividas = new ArrayList<>();
    }

    public List<Divida> getDividas() {
        return dividas;
    }

    public void setDividas(List<Divida> dividas) {
        this.dividas = dividas;
    }

    public Cliente() {
        dividas = new ArrayList<>();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
