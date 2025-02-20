package com.service.movies.kinopoiskapi.mappers;

import com.service.movies.kinopoiskapi.dto.GenreApiDto;
import com.service.movies.models.entities.Genre;

public interface GenreApiMapper {

    Genre toEntity(GenreApiDto apiDto);

}
