package com.service.movies.kinopoiskapi.mappers;

import com.service.movies.kinopoiskapi.dto.MovieApiDto;
import com.service.movies.models.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {GenreApiMapper.class, CountryApiMapper.class, PersonApiMapper.class})
public interface MovieApiMapper {

    @Mapping(target = "kpRating", source = "rating.kp")
    @Mapping(target = "imdbRating", source = "rating.imdb")
    @Mapping(target = "posterUrl", source = "poster.url")
    @Mapping(target = "logoUrl", source = "logo.url")
    @Mapping(target = "enName", source = "alternativeName")
    Movie toEntity(MovieApiDto apiDto);
}
