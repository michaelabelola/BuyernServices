package com.buyern.notification.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PushRecipient extends BaseRecipient {
    private String deviceId;
}
