package com.buyern.notification.serializers;

import com.buyern.notification.models.EmailRecipient;
import com.buyern.notification.models.NotificationModel;
import com.buyern.notification.models.PhoneRecipient;
import com.buyern.notification.models.PushRecipient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;

@Slf4j
@ConstructorBinding
public class NotificationModelDeserializer implements Deserializer<NotificationModel<?>> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public NotificationModel<?> deserialize(String topic, byte[] data) {
        return deserializer(topic, data);
    }

    @Override
    public NotificationModel<?> deserialize(String topic, Headers headers, byte[] data) {
        return deserializer(topic, data);
    }

    private NotificationModel<?> deserializer(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            log.info("Deserializing " + topic + "...");
           return switch (topic) {
                case "EmailNotificationService" -> objectMapper.readValue(data, new TypeReference<NotificationModel<EmailRecipient>>() {});

                case "PhoneNotificationService" -> objectMapper.readValue(data, new TypeReference<NotificationModel<PhoneRecipient>>() {
                });
                case "PushNotificationService" -> objectMapper.readValue(data, new TypeReference<NotificationModel<PushRecipient>>() {
                });
                default -> throw new IllegalStateException("Unexpected value: " + topic);
            };
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[]: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

