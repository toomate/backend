package com.toomate.backend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsumoNotificationBody implements NotificationBody {
    String nome;
    Integer quantidadeMinima;
    Double quantidadeAtual;
}
