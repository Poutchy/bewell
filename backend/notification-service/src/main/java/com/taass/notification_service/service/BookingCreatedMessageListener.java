package com.taass.notification_service.service;

import com.taass.BookingMessageDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingCreatedMessageListener {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = "${rabbitmq.booking-created-queue}")
    @Transactional
    public Boolean handleBookingCreatedMessage(BookingMessageDTO bookingMessageDTO) {
      log.info("Received Booking Created for order ID: {}", bookingMessageDTO.getSalonId());

      try {
          SimpleMailMessage message = new SimpleMailMessage();

          message.setFrom("bewell.notification00@gmail.com");
          message.setTo("matteobussolino7@gmail.com");
          message.setSubject("Test email");
          message.setText("This is a sample email body");

          mailSender.send(message);
          return true;
      } catch (Exception e) {
          return false;
      }
    }

}
