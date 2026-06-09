package com.toomate.backend.schedules;

import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.service.BoletoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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
        List<Boleto> boletos = boletoService.buscarPorDias(7);
        boletos.addAll(boletoService.buscarPorDias(0));
        log.info("Boletos encontrados: {}", boletos.size());
        notificarMicroservico(boletos);
    }

    private void notificarMicroservico(List<Boleto> boletosVencendo){
        for (Boleto b : boletosVencendo){
            producerRabbitMQ.enviarNotif(new NotificationDto(b));
        }
    }
}
