package com.buyern.notification.controllers;

import com.buyern.notification.models.EmailRecipient;
import com.buyern.notification.models.NotificationModel;
import com.buyern.notification.services.NotificationService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Data
@RestController
@RequestMapping("api/v1/")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("pushNotification")
    public ResponseEntity<Boolean> sendPushNotification(@RequestBody NotificationModel<EmailRecipient> notificationModel) {
        return ResponseEntity.ok(notificationService.sendPushNotification(notificationModel));
    }

    @PostMapping("mail")
    public ResponseEntity<Boolean> sendMail(@RequestBody NotificationModel<EmailRecipient> notificationModel) {
        return ResponseEntity.ok(notificationService.sendMailNotification(notificationModel));
    }

    @PostMapping("phoneMessage")
    public ResponseEntity<Boolean> sendPhoneMessage(@RequestBody NotificationModel<EmailRecipient> notificationModel) {
        return ResponseEntity.ok(notificationService.sendPhoneNotification(notificationModel));
    }
}
