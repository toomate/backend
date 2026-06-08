package com.toomate.backend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BoletoNotificationBody implements NotificationBody{
    private String descricao;
    private LocalDate vencimento;
    private Double valor;
}
