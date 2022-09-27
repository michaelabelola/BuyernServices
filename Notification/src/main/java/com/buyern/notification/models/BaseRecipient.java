package com.buyern.notification.models;

import com.buyern.notification.enums.RecipientType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRecipient {
    private RecipientType type;
    private String uid;
    private String name;
}
