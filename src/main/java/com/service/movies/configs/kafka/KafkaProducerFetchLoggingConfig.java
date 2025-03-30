package com.service.movies.configs.kafka;

import com.service.movies.models.events.PageMovieFetchedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
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

@Configuration
public class KafkaProducerFetchLoggingConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    NewTopic createMovieFetchLogsTopic() {
        return TopicBuilder.name("movie-fetch-log-events-topic")
                .partitions(2)
                .replicas(2)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
    @Bean
    public ProducerFactory<String, PageMovieFetchedEvent> movieFetchLogsProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PageMovieFetchedEvent> kafkaMovieFetchLogs() {
        return new KafkaTemplate<>(movieFetchLogsProducerFactory());
    }
}
