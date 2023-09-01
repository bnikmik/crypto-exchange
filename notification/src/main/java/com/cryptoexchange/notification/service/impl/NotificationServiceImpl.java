package com.cryptoexchange.notification.service.impl;

import com.cryptoexchange.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmailNotification(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    @KafkaListener(topics = "${crypto-exchange.kafka.request-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendEmailNotificationByKafka(String text) {
        log.debug("Received KAFKA message: {}", text);
        int semicolonIndex = text.indexOf(";");
        String userEmail = text.substring(0, semicolonIndex);
        String uuidDeal = text.substring(semicolonIndex + 1);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(email);
        message.setTo(userEmail);
        message.setSubject("Сделка на Crypto-Exchange");
        message.setText("Здравствуйте. Перейдите по ссылке для совершения сделки: http://localhost:9000/deals/" + uuidDeal);

        mailSender.send(message);
    }
}
