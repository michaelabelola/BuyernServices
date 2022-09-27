package com.buyern.notification.models;

import com.buyern.notification.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationModel<R> {
    private NotificationType type;
    private R recipient;
    private String title;
    private String icon;
    @Nullable
    private String content;
}
