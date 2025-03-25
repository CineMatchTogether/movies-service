package com.service.movies.kinopoiskapi.controllers;

import com.service.movies.kinopoiskapi.services.KinoPoiskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kinopoisk")
@RequiredArgsConstructor
public class KinoPoiskApiController {

    private final KinoPoiskApiService kinoPoiskApiService;

    @GetMapping("/fetchpages")
    public ResponseEntity<String> fetchMoviesAsync(@RequestParam int startPage, @RequestParam int endPage) {
        if (startPage > endPage || startPage < 0) {
            return ResponseEntity.badRequest().body("Invalid page range");
        }
        kinoPoiskApiService.asyncFetchPages(startPage, endPage);

        return ResponseEntity.accepted().body("Fetch started for pages " + startPage + " to " + endPage);
    }
}
