package com.service.movies.mappers;

import com.service.movies.models.dto.MovieDto;
import com.service.movies.models.entities.Movie;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {GenreMapper.class, CountryMapper.class, PersonMapper.class})
public interface MovieMapper {
    Movie toEntity(MovieDto movieDto);

    MovieDto toDto(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Movie partialUpdate(MovieDto movieDto, @MappingTarget Movie movie);

}