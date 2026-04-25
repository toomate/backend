package com.toomate.backend.integration;

import com.toomate.backend.config.rabbit.RabbitPropertiesConfiguration;
import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoNotificationDto;
import com.toomate.backend.model.Insumo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProducerRabbitMQ {


    private final RabbitTemplate rabbitTemplate;
    private final RabbitPropertiesConfiguration properties;

    public ProducerRabbitMQ(RabbitTemplate rabbitTemplate, RabbitPropertiesConfiguration properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void enviarNotif(Insumo insumo, Double atual){

        String exchangeName = properties.exchange().name();

        InsumoNotificationDto notificationDto = InsumoMapperDto.toNotification(insumo, atual);

        try{
            System.out.printf("Quantidade atual do insumo %s é %.2f, abaixo do mínimo de %d%n\nEnviando notificação...",
                    insumo.getNome(), atual, insumo.getQtdMinima());

        rabbitTemplate.convertAndSend(exchangeName, "", notificationDto);

        } catch (RuntimeException e){
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
