package com.service.movies.kinopoiskapi.mappers.defaultmappers;

import com.service.movies.kinopoiskapi.dto.GenreApiDto;
import com.service.movies.kinopoiskapi.mappers.GenreApiMapper;
import com.service.movies.models.entities.Genre;
import com.service.movies.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreApiMapperDefault implements GenreApiMapper {

    private final GenreService genreService;
    @Override
    public Genre toEntity(GenreApiDto apiDto) {
        return genreService.getOrCreate(apiDto.name());
    }
}
