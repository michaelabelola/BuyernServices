package com.buyern.notification.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConstructorBinding
public class KafkaTopicsConfig {
    @Value("${spring.application.name}")
    String notificationTopic;

    @Bean
    public NewTopic passwordResetNotification() {
        return TopicBuilder.name(notificationTopic).build();
    }
}
