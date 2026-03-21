package com.toomate.backend.integration;

import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoNotificationDto;
import com.toomate.backend.model.Insumo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EnviarNotificacao {

    private final WebClient webClient;
    private final String notificacaoEndpointUrl;

    public EnviarNotificacao(WebClient webClient,
                             @Value("${notificacao.endpoint.url}") String notificacaoEndpointUrl) {
        this.webClient = webClient;
        this.notificacaoEndpointUrl = notificacaoEndpointUrl;
    }

    public void enviarNotif(Insumo insumo, Double atual){

        InsumoNotificationDto notificationDto = InsumoMapperDto.toNotification(insumo, atual);

        try{
            System.out.printf("Quantidade atual do insumo %s é %.2f, abaixo do mínimo de %d%n\nEnviando notificação...",
                    notificationDto.getNome(), notificationDto.getQuantidadeAtual(), notificationDto.getQuantidadeMinima());

            webClient.post()
                 .uri(notificacaoEndpointUrl)
                 .bodyValue(notificationDto)
                 .retrieve()
                 .toBodilessEntity()
                 .block();

        } catch (RuntimeException e){
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
