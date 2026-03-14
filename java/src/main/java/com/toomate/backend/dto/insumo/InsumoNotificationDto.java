package com.toomate.backend.dto.insumo;

import lombok.Data;

@Data
public class InsumoNotificationDto {
    private Integer id;
    private String nome;
    private Double quantidadeAtual;
    private Integer quantidadeMinima;
    private String unidadeMedida;

}
