package com.service.movies.kinopoiskapi.controllers;

import com.service.movies.kinopoiskapi.services.KinoPoiskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kinopoisk")
@RequiredArgsConstructor
public class KinoPoiskApiController {

    private final KinoPoiskApiService kinoPoiskApiService;

    @GetMapping("/fetchpage/{number}")
    public ResponseEntity<?> fetchMoviesOnPage(@PathVariable int number) {
        kinoPoiskApiService.fetchMovies(number);

        return ResponseEntity.ok().build();
    }

}
