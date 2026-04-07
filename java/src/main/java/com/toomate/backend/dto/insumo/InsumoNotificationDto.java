package com.toomate.backend.dto.insumo;

import com.toomate.backend.model.Insumo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsumoNotificationDto {
    private String id;
    private LocalDateTime timestamp = LocalDateTime.now();
    private BodyInsumoNotification body;

    InsumoNotificationDto(Insumo insumo, Double quantidadeAtual) {
        this.id = "e" + insumo.getIdInsumo();
        this.body = new BodyInsumoNotification(
                insumo.getNome(),
                insumo.getQtdMinima(),
                quantidadeAtual,
                insumo.getUnidadeMedida()
        );
    }

}


