package com.service.movies.models.dto;

import java.util.Set;

/**
 * DTO for {@link com.service.movies.models.entities.Movie}
 */
public record MovieDto(Long id, String name, String description, String shortDescription, Integer year, Double kpRating,
                       Double imdbRating, Integer movieLength, String posterUrl, String logoUrl, Set<GenreDto> genres,
                       Set<CountryDto> countries, Set<PersonDto> persons) {
}