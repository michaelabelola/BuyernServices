package com.buyern.notification.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneRecipient extends BaseRecipient {
    private String phone;
}
