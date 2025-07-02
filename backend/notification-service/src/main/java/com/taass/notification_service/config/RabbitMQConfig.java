package com.taass.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.routingkey.booking-created}")
    private String bookingCreatedTicketRoutingKey;

    @Value("${app.rabbitmq.queue.booking-created-queue}")
    private String bookingCreatedQueue;

    @Bean
    Queue BookingQueue() {
        return new Queue(bookingCreatedQueue, true);
    }

    @Bean
    public Exchange beWellExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    // 3. Crea il Binding tra la coda e l'exchange usando la routing key
    @Bean
    public Binding BookingBinding(Queue BookingQueue, Exchange beWellExchange) {
        return BindingBuilder.bind(BookingQueue)
                .to(beWellExchange)
                .with(bookingCreatedTicketRoutingKey) // La chiave usata per inviare
                .noargs(); // Necessario per DirectExchange
    }

    // 4. Configura Jackson come convertitore di messaggi (per JSON)
    // Spring Boot lo fa in parte automaticamente, ma è bene essere espliciti
    // e assicurarsi che entrambi i servizi usino lo stesso meccanismo.
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        // Usiamo l'ObjectMapper configurato da Spring Boot
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
