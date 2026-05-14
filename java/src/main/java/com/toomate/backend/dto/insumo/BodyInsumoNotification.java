package com.toomate.backend.dto.insumo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyInsumoNotification {
    String nome;
    Integer quantidadeMinima;
    Double quantidadeAtual;
//    String unidadeMedida;
}
