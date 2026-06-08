package com.toomate.backend.dto.notification;

import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Lote;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private String id;
    private LocalDateTime timestamp = LocalDateTime.now();
    private NotificationBody body;

    public NotificationDto(Insumo insumo, Double quantidadeAtual) {
        this.id = "e" + insumo.getIdInsumo();
        this.body = new InsumoNotificationBody(
                insumo.getNome(),
                insumo.getQtdMinima(),
                quantidadeAtual
        );
    }

    public NotificationDto(Boleto boleto){
        this.id = "b" + boleto.getIdBoleto();
        this.body = new BoletoNotificationBody(
                boleto.getDescricao(),
                boleto.getDataVencimento(),
                boleto.getValor()
        );
    }

    public NotificationDto(Lote lote, String insumo){
        this.id = "v" + lote.getIdLote();
        this.body = new VencimentoNotificationBody(
                insumo,
                lote.getDataValidade()
        );
    }

}


