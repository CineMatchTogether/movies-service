package com.service.movies.handlers;

import com.service.movies.kinopoiskparser.KinoPoiskParserService;
import com.service.movies.models.events.FetchingUserHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "fetch-history-request-topic", groupId = "fetch-user-history-events", containerFactory = "userHistoryKafkaListenerContainerFactory")
public class UserHistoryEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KinoPoiskParserService kinoPoiskParserService;

    @KafkaHandler
    public void handle(FetchingUserHistoryEvent event, @Header(KafkaHeaders.RECEIVED_KEY) UUID userId) {
        logger.info("Received event with key: {} and KinoPoiskId: {}", userId, event.kinoPoiskId());

        kinoPoiskParserService.fetchAndParseKinoPoiskPage(userId, event.kinoPoiskId(), event.cookie());
    }

}
