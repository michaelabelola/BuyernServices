package com.buyern.notification.services;

import com.buyern.notification.models.EmailRecipient;
import com.buyern.notification.models.NotificationModel;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public boolean sendPushNotification(NotificationModel<EmailRecipient> notificationModel) {
        return true;
    }

    public boolean sendMailNotification(NotificationModel<EmailRecipient> notificationModel) {
        return false;
    }

    public boolean sendPhoneNotification(NotificationModel<EmailRecipient> notificationModel) {
        return false;
    }
}
