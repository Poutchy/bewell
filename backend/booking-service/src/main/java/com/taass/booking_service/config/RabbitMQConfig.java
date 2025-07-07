package com.taass.booking_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private String bookingCreatedRoutingKey;

    @Value("${app.rabbitmq.queue.booking-created-queue}")
    private String bookingCreatedQueue;

    @Bean
    public Exchange BeWellExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue bookingCreatedQueue() {
        return new Queue(bookingCreatedQueue, true);
    }

    @Bean
    public Binding readyTicketBinding(Queue bookingCreatedQueue, Exchange beWellExchange) {
        return BindingBuilder.bind(bookingCreatedQueue)
                .to(beWellExchange)
                .with(bookingCreatedRoutingKey) // the routing key used by booking-service to send messages
                .noargs(); // Necessario per DirectExchange
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}