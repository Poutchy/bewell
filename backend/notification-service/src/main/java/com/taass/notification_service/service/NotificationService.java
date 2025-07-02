package com.taass.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotification() {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//
//            message.setFrom("bewell.notification00@gmail.com");
//            message.setTo("matteobussolino7@gmail.com");
//            message.setSubject("Test email");
//            message.setText("This is a sample email body");
//
//            mailSender.send(message);
//            return "Email sent successfully";
//        } catch (Exception e) {
//            return "Error sending email: " + e.getMessage();
//        }
    }
}
