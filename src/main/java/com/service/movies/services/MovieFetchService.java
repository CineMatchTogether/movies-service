package com.service.movies.services;

import com.service.movies.models.entities.Movie;
import com.service.movies.models.events.PageMovieFetchedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieFetchService {

    private final KafkaTemplate<String, PageMovieFetchedEvent> kafkaTemplate;
    private static final String TOPIC = "movie-fetch-log-events-topic";

    public void logFetchedPage(List<Movie> movies, int page) {
        PageMovieFetchedEvent event = new PageMovieFetchedEvent(page,
                movies.stream().filter(m -> m.getDescription() == null).count(),
                movies.stream().filter(m -> m.getShortDescription() == null).count(),
                movies.stream().filter(m -> m.getPosterUrl() == null).count());

        kafkaTemplate.send(TOPIC, String.valueOf(page), event);
    }
}
