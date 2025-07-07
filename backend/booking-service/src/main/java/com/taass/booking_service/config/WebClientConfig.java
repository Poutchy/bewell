package com.taass.booking_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${services.salon.url}") // Prenderemo da application.properties
    private String salonServiceUrl;

    @Value("${services.client.url}") // Prenderemo da application.properties
    private String clientServiceUrl;

    @Bean
    public WebClient salonWebClient(WebClient.Builder builder) {
        return builder.baseUrl(salonServiceUrl).build();
    }

    @Bean
    public WebClient clientWebClient(WebClient.Builder builder) {
        return builder.baseUrl(clientServiceUrl).build();
    }
}
