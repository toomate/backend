package com.toomate.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toomate.backend.config.rabbit.RabbitPropertiesConfiguration;
import com.toomate.backend.mapper.insumo.InsumoMapper;
import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Insumo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

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

    public void enviarNotif(NotificationDto notificationDto){
        String exchangeName = properties.exchange().name();

        try{

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
