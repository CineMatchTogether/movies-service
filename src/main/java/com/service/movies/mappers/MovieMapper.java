package com.service.movies.mappers;

import com.service.movies.models.dto.MovieDto;
import com.service.movies.models.entities.Movie;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {
    Movie toEntity(MovieDto movieDto);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "persons", ignore = true)
    MovieDto toDto(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Movie partialUpdate(MovieDto movieDto, @MappingTarget Movie movie);

}