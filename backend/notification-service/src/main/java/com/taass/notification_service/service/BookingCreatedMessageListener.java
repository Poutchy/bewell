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

    @RabbitListener(queues = "${app.rabbitmq.queue.booking-created-queue}")
    @Transactional
    public void handleBookingCreatedMessage(BookingMessageDTO bookingMessageDTO) {
      log.info("Received Booking Created for order ID: {}", bookingMessageDTO.getOrderId());

      try {
          SimpleMailMessage message = new SimpleMailMessage();

          String emailBody = String.format(
                  """
                          Dear Customer %s %s,
                          
                          Thank you for your booking at %s!
                          
                          Service: %s
                          Booking Start At: %s
                          Booking End At: %s
                          Already payed: %s
                          
                          If you have any questions, please contact us
                          at %s or call us at %s.
                          
                          You can find us at the following address:
                            %s
                          
                          Best regards,
                          The Team""",
            bookingMessageDTO.getClientName(),
            bookingMessageDTO.getClientSurname(),
            bookingMessageDTO.getSalonName(),
            bookingMessageDTO.getServiceName(),
            bookingMessageDTO.getTStart(),
            bookingMessageDTO.getTEnd(),
            bookingMessageDTO.getPayed() ? "Yes" : "No",
            bookingMessageDTO.getSalonEmail(),
            bookingMessageDTO.getSalonPhone(),
            bookingMessageDTO.getSalonAddress()
          );

          message.setFrom("bewell.notification00@gmail.com");
          message.setTo(bookingMessageDTO.getClientEmail());
          message.setSubject("Booking Confirmation - Order ID: " + bookingMessageDTO.getOrderId());
          message.setText(emailBody);

          mailSender.send(message);
      } catch (Exception e) {
            log.error("Failed to send booking confirmation email for order ID {}: {}", bookingMessageDTO.getOrderId(), e.getMessage(), e);
      }
    }

}
