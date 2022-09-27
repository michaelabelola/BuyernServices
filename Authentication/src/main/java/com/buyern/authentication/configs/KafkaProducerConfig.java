package com.buyern.authentication.configs;

import com.buyern.authentication.notification.NotificationModel;
import com.buyern.authentication.serializers.CustomSerializer;
import com.buyern.authentication.notification.NotificationSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig(Class<?> valueSerializerClass) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);
        return props;
    }

    @Bean
    public ProducerFactory<String, NotificationModel> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig(NotificationSerializer.class));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig(CustomSerializer.class)));
    }

    @Bean
    public KafkaTemplate<String, NotificationModel> kafkaNotificationTemplate(ProducerFactory<String, NotificationModel> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
