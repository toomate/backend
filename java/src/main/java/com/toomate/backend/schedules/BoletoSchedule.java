package com.toomate.backend.schedules;

import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.service.BoletoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoletoSchedule {

    BoletoService boletoService;
    ProducerRabbitMQ producerRabbitMQ;


    public BoletoSchedule(BoletoService boletoService, ProducerRabbitMQ producerRabbitMQ) {
        this.producerRabbitMQ = producerRabbitMQ;
        this.boletoService = boletoService;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void consultarBoletosSemana(){
        notificarMicroservico(boletoService.buscarPorDias(7));
        notificarMicroservico(boletoService.buscarPorDias(0));
    }

    private void notificarMicroservico(List<Boleto> boletosVencendo){
        for (Boleto b : boletosVencendo){
            producerRabbitMQ.enviarNotif(new NotificationDto(b));
        }
    }
}
