package com.service.movies.kinopoiskapi.mappers.defaultmappers;

import com.service.movies.kinopoiskapi.dto.PersonApiDto;
import com.service.movies.kinopoiskapi.mappers.PersonApiMapper;
import com.service.movies.models.entities.Person;
import com.service.movies.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonApiMapperDefault implements PersonApiMapper {

    private final PersonService personService;

    @Override
    public Person toEntity(PersonApiDto apiDto) {
        return personService.getOrCreate(apiDto.id(), apiDto.photo(), apiDto.name(), apiDto.profession());
    }
}
