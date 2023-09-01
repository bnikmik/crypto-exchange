package com.cryptoexchange.notification.service;

public interface NotificationService {

    void sendEmailNotification(String to, String subject, String text);

    void sendEmailNotificationByKafka(String message);
}
