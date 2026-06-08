package com.toomate.backend.schedules;

import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.service.LoteService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VencimentoSchedule {
    LoteService LoteService;
    ProducerRabbitMQ producerRabbitMQ;


    public VencimentoSchedule(LoteService LoteService, ProducerRabbitMQ producerRabbitMQ) {
        this.producerRabbitMQ = producerRabbitMQ;
        this.LoteService = LoteService;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void consultarBoletosSemana(){
        notificarMicroservico(LoteService.buscarPorDias(7));
        notificarMicroservico(LoteService.buscarPorDias(0));
    }

    private void notificarMicroservico(List<NotificationDto> lotesVencendo){
        for (NotificationDto loteVencendo : lotesVencendo){
            producerRabbitMQ.enviarNotif(loteVencendo);
        }
    }
    
}
