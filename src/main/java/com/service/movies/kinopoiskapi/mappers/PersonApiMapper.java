package com.service.movies.kinopoiskapi.mappers;


import com.service.movies.kinopoiskapi.dto.PersonApiDto;
import com.service.movies.models.entities.Person;

public interface PersonApiMapper {

    Person toEntity(PersonApiDto apiDto);
}
