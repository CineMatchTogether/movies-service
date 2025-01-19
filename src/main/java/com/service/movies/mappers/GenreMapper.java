package com.service.movies.mappers;

import com.service.movies.models.dto.GenreDto;
import com.service.movies.models.entities.Genre;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GenreMapper {
    Genre toEntity(com.service.movies.models.dto.GenreDto genreDto);

    GenreDto toDto(Genre genre);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Genre partialUpdate(GenreDto genreDto, @MappingTarget Genre genre);

}