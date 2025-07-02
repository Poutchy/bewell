package com.taass.notification_service.controller;

import com.taass.notification_service.service.NotificationService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final JavaMailSender mailSender;
    private final NotificationService notificationService;

    @RequestMapping("/notify-booking")
    public String sendEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("bewell.notification00@gmail.com");
            message.setTo("matteobussolino7@gmail.com");
            message.setSubject("Test email");
            message.setText("This is a sample email body");

            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }

    @RequestMapping("/send-invoice")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("bewell.notification00@gmail.com");
            helper.setTo("matteobussolino7@gmail.com");
            helper.setSubject("Test email with attachment");
            helper.setText("Attachment document below");

            helper.addAttachment("invoice.pdf", new File("src/main/resources/static/invoice.pdf"));

            mailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
