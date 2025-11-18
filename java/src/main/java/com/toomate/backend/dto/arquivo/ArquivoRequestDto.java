package com.toomate.backend.dto.arquivo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArquivoRequestDto {
    @NotBlank
    private String nomeOriginal;
    @NotBlank
    private String chave;
    @NotBlank
    private String nomeBucket;

    public ArquivoRequestDto(String nomeOriginal, String nomeBucket) {
        this.nomeOriginal = nomeOriginal;
        this.nomeBucket = nomeBucket;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
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
}
