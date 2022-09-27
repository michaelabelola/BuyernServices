package com.buyern.notification.services;

import com.buyern.notification.models.EmailRecipient;
import com.buyern.notification.models.NotificationModel;
import com.buyern.notification.models.PhoneRecipient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {
    private final EmailService emailService;

    @KafkaListener(topics = "EmailNotificationService", groupId = "NotificationService",contentTypeConverter = "")
    void EmailNotificationListener(ConsumerRecord<String, NotificationModel<EmailRecipient>> data) {
//        EmailNotificationDto emailNotificationDto = new ObjectMapper().convertValue(data.value(), new TypeReference<EmailNotificationDto>() {});
//        log.info(data.key() + "  ::::::::::::::::::  " + data.value().get("type"));
        log.info(data.key() + "  :::::::::email shit:::::::::  " + data.value().getRecipient().getEmail());
//
//        SimpleMailMessage completionEmail = new SimpleMailMessage();
//        completionEmail.setTo(emailNotificationDto.getEmail());
//        completionEmail.setSubject("Congratulations! Your Buyern registration is successful");
//        assert emailNotificationDto.getContent() != null;
//        completionEmail.setText(emailNotificationDto.getContent());
//        emailService.sendEmail(completionEmail);
    }

    @KafkaListener(topics = "PhoneNotificationService", groupId = "NotificationService",contentTypeConverter = "")
    void phoneResetNotificationListener(ConsumerRecord<String, NotificationModel<PhoneRecipient>> data) {
        log.info(data.key() + "  :::::::::Phone shit:::::::::  " + data.value().getType());
    }
}
