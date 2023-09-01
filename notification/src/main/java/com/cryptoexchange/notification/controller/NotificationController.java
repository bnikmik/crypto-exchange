package com.cryptoexchange.notification.controller;

import com.cryptoexchange.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String message) {

        notificationService.sendEmailNotification(to, subject, message);

        return "Email sent successfully!";
    }
}
