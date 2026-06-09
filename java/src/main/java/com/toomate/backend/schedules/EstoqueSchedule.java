package com.toomate.backend.schedules;

import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.service.InsumoService;
import com.toomate.backend.service.LoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EstoqueSchedule {

    private final ProducerRabbitMQ producerRabbitMQ;
    private final LoteService loteService;
    private final InsumoService insumoService;

    public EstoqueSchedule(ProducerRabbitMQ producerRabbitMQ, LoteService loteService, InsumoService insumoService) {
        this.producerRabbitMQ = producerRabbitMQ;
        this.loteService = loteService;
        this.insumoService = insumoService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void consultarInsumosPoucoEstoque(){
        List<Insumo> ativos = insumoService.listar();

        HashMap<Insumo, Double> alertas = new HashMap<>();

        for (Insumo insumo : ativos) {
            try {
                Double total = loteService.getEstoqueInsumo(insumo);
                if (total == null) {
                    total = 0.0;
                }

                if (total < insumo.getQtdMinima()) {
                    alertas.put(insumo, total);
                }
            } catch (Exception e) {
                log.error("Erro ao calcular estoque do insumo {}: {}", insumo.getNome(), e.getMessage());
            }
        }

        if (!alertas.isEmpty()) {
            notificarMicroservico(alertas);
        }
    }

    private void notificarMicroservico(HashMap<Insumo, Double> alertas){
        for (Map.Entry<Insumo, Double> e : alertas.entrySet()){
            producerRabbitMQ.enviarNotif(new NotificationDto(e.getKey(), e.getValue()));
        }
    }

}
