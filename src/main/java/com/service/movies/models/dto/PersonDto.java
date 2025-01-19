package com.service.movies.models.dto;

/**
 * DTO for {@link com.service.movies.models.entities.Person}
 */
public record PersonDto(Long id, String name, String photoUrl, String profession) {
}