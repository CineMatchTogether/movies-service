package com.service.movies.kinopoiskparser;

import com.service.movies.models.events.UserHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHistoryService {

    private final KafkaTemplate<UUID, UserHistoryEvent> kafkaUserHistory;

    private static final String TOPIC = "user-history-topic";

    public void sendUserHistory(UUID userId, UserHistoryEvent event) {
        kafkaUserHistory.send(TOPIC, userId, event);
    }
}
