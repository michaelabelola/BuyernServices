package com.buyern.notification.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public EmailService(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private static final String EMAIL_ADDRESS = "noreply@esettlementgroup.com";

    @Async
    public void sendEmail(SimpleMailMessage mail) {
//        NotificationModel
        try {
            mail.setFrom(EMAIL_ADDRESS);
            mailSender.send(mail);
        } catch (Exception e) {
            LOGGER.error("Error sending onboarding mail {}", e.getMessage());
        }

    }

}
