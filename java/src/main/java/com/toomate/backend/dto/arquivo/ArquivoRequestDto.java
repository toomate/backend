package com.toomate.backend.dto.arquivo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArquivoRequestDto {
    @NotBlank
    private String nomeOriginal;
    @NotBlank
    private String nomeUsuario;
    @NotBlank
    private String chave;
    @NotBlank
    private String nomeBucket;
    private Integer idBoleto;

    public ArquivoRequestDto(String nomeOriginal, String nomeUsuario, String nomeBucket, Integer idBoleto) {
        this.nomeOriginal = nomeOriginal;
        this.nomeUsuario = nomeUsuario;
        this.nomeBucket = nomeBucket;
        this.idBoleto = idBoleto;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNomeBucket() {
        return nomeBucket;
    }

    public void setNomeBucket(String nomeBucket) {
        this.nomeBucket = nomeBucket;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }
}
