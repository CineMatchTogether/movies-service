package com.service.movies.services;

import com.service.movies.mappers.PersonMapper;
import com.service.movies.models.dto.PersonDto;
import com.service.movies.models.entities.Person;
import com.service.movies.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonMapper personMapper;

    private final PersonRepository personRepository;

    public Page<PersonDto> getList(Pageable pageable) {
        Page<Person> people = personRepository.findAll(pageable);
        return people.map(personMapper::toDto);
    }

    public PersonDto getOne(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        return personMapper.toDto(personOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<PersonDto> getMany(List<Long> ids) {
        List<Person> people = personRepository.findAllById(ids);
        return people.stream()
                .map(personMapper::toDto)
                .toList();
    }

    public PersonDto create(PersonDto dto) {
        Person person = personMapper.toEntity(dto);
        Person resultPerson = personRepository.save(person);
        return personMapper.toDto(resultPerson);
    }

    public PersonDto patch(Long id, PersonDto patchNode) throws IOException {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        personMapper.partialUpdate(patchNode, person);

        Person resultPerson = personRepository.save(person);
        return personMapper.toDto(resultPerson);
    }

    public PersonDto delete(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            personRepository.delete(person);
        }
        return personMapper.toDto(person);
    }

    public void deleteMany(List<Long> ids) {
        personRepository.deleteAllById(ids);
    }
}
