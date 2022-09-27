package com.buyern.notification.models;

import com.buyern.notification.enums.RecipientType;
import lombok.Data;

@Data
public class Recipient {
    private RecipientType type;
    private String uid;
    private String name;
    private String phone;
    private String email;
    private String deviceId;
}
