package com.buyern.notification.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CustomDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        return deserializer(topic, data);
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        return deserializer(topic, data);
    }

    private T deserializer(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            log.info("Deserializing " + topic + "...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[]");
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
