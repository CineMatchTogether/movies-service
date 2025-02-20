package com.service.movies.kinopoiskapi.mappers;

import com.service.movies.kinopoiskapi.dto.CountryApiDto;
import com.service.movies.models.entities.Country;

public interface CountryApiMapper {

    Country toEntity(CountryApiDto apiDto);
}
