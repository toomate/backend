package com.toomate.backend.schedules;

import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Lote;
import com.toomate.backend.service.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Component
public class VencimentoSchedule {
    private final LoteService loteService;
    private final ProducerRabbitMQ producerRabbitMQ;


    public VencimentoSchedule(LoteService loteService, ProducerRabbitMQ producerRabbitMQ) {
        this.producerRabbitMQ = producerRabbitMQ;
        this.loteService = loteService;
    }

    @Scheduled(
            cron = "0 0 */1 * * *",
            zone = "America/Sao_Paulo"
    )
    public void consultarBoletosSemana(){
        List<NotificationDto> lotes = loteService.buscarPorDias(7);
        lotes.addAll(loteService.buscarPorDias(0));
        log.info("Boletos encontrados: {}", lotes.size());
        notificarMicroservico(lotes);
    }

    private void notificarMicroservico(List<NotificationDto> lotesVencendo){
        for (NotificationDto loteVencendo : lotesVencendo){
            producerRabbitMQ.enviarNotif(loteVencendo);
        }
    }
    
}
