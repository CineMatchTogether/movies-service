package com.service.movies.kinopoiskapi.mappers.defaultmappers;

import com.service.movies.kinopoiskapi.dto.CountryApiDto;
import com.service.movies.kinopoiskapi.mappers.CountryApiMapper;
import com.service.movies.models.entities.Country;
import com.service.movies.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryApiMapperDefault implements CountryApiMapper {

    private final CountryService countryService;
    @Override
    public Country toEntity(CountryApiDto apiDto) {
        return countryService.getOrCreate(apiDto.name());
    }
}
