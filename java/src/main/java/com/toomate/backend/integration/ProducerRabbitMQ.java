package com.toomate.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toomate.backend.config.rabbit.RabbitPropertiesConfiguration;
import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoNotificationDto;
import com.toomate.backend.model.Insumo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class ProducerRabbitMQ {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitPropertiesConfiguration properties;
    private final ObjectMapper objectMapper;

    public ProducerRabbitMQ(RabbitTemplate rabbitTemplate, RabbitPropertiesConfiguration properties, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    public void enviarNotif(Insumo insumo, Double atual){

        String exchangeName = properties.exchange().name();

        InsumoNotificationDto notificationDto = InsumoMapperDto.toNotification(insumo, atual);

        try{
            log.info("Quantidade atual do insumo %s é %.2f, abaixo do mínimo de %d".formatted(
                    insumo.getNome(), atual, insumo.getQtdMinima()));

            String jsonMessage = objectMapper.writeValueAsString(notificationDto);

            rabbitTemplate.convertAndSend(exchangeName, "", jsonMessage, message -> {
                MessageProperties props = message.getMessageProperties();
                props.setContentType("application/json");
                props.setContentEncoding("UTF-8");
                return message;
            });

            log.info("Mensagem produzida para o RabbitMQ");
        } catch (RuntimeException e){
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao serializar mensagem para JSON", e);
        }
    }
}
