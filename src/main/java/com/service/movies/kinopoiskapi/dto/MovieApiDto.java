package com.service.movies.kinopoiskapi.dto;

import java.util.List;

public record MovieApiDto(
        long id,
        String name,
        int year,
        String description,
        String shortDescription,
        RatingApiDto rating,
        int movieLength,
        PosterApiDto poster,
        List<GenreApiDto> genres,
        List<CountryApiDto> countries,
        List<PersonApiDto> persons,
        LogoApiDto logo
) {}

