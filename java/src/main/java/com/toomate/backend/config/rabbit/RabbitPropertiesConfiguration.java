package com.toomate.backend.config.rabbit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker")
public record RabbitPropertiesConfiguration(
        @Value("${broker.exchange.name}")
        Exchange exchange,
        @Value("${broker.queue.name}")
        Queue queue
) {
    public record Exchange(String name) {
    }

    public record Queue(String name) {
    }
}