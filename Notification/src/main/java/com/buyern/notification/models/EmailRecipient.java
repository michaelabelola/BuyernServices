package com.buyern.notification.models;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString(callSuper = true)
public class EmailRecipient extends BaseRecipient {
    private String email;
}
