package com.service.movies.configs.kafka;

import com.service.movies.models.events.UserHistoryEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaProducerUserHistoryConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    NewTopic createUserHistoryTopic() {
        return TopicBuilder.name("user-history-topic")
                .partitions(2)
                .replicas(2)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
    @Bean
    public ProducerFactory<UUID, UserHistoryEvent> userHistoryProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<UUID, UserHistoryEvent> kafkaUserHistory() {
        return new KafkaTemplate<>(userHistoryProducerFactory());
    }
}
