package com.toomate.backend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VencimentoNotificationBody implements NotificationBody {
 private String insumo;
 private LocalDate vencimento;

}
