package com.service.movies.kinopoiskapi.services;

import com.service.movies.kinopoiskapi.dto.MovieListResponse;
import com.service.movies.kinopoiskapi.mappers.MovieApiMapper;
import com.service.movies.models.entities.Movie;
import com.service.movies.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KinoPoiskApiService {

    private final RestTemplate restTemplate;
    private final MovieService movieService;
    private final MovieApiMapper movieApiMapper;

    private static final Logger logger = LoggerFactory.getLogger(KinoPoiskApiService.class);
    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/movie";

    @Value("${property.app.kino-poisk-api-key}")
    private String ApiKey;

    public void fetchMovies(int page) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", ApiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("page", page)
                .queryParam("limit", 200)
                .queryParam("type", "movie")
                .queryParam("sortField", "rating.kp")
                .queryParam("sortField", "rating.imdb")
                .queryParam("sortType", -1)
                .queryParam("sortType", -1)
                .queryParam("notNullFields", "name")
                .queryParam("notNullFields", "poster.url")
                .queryParam("selectFields", "id")
                .queryParam("selectFields", "name")
                .queryParam("selectFields", "description")
                .queryParam("selectFields", "shortDescription")
                .queryParam("selectFields", "slogan")
                .queryParam("selectFields", "type")
                .queryParam("selectFields", "year")
                .queryParam("selectFields", "rating")
                .queryParam("selectFields", "movieLength")
                .queryParam("selectFields", "genres")
                .queryParam("selectFields", "countries")
                .queryParam("selectFields", "poster")
                .queryParam("selectFields", "logo")
                .queryParam("selectFields", "persons")
                .toUriString();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieListResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                MovieListResponse.class
        );

        List<Movie> movies = response.getBody().docs().stream().map(movieApiMapper::toEntity).toList();

        movieService.saveAll(movies);

        logger.info("Save movies on page {}", page);
    }
}
