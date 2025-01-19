package com.service.movies.contollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.service.movies.models.dto.PersonDto;
import com.service.movies.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public PagedModel<PersonDto> getList(@ParameterObject Pageable pageable) {
        Page<PersonDto> personDtos = personService.getList(pageable);
        return new PagedModel<>(personDtos);
    }

    @GetMapping("/{id}")
    public PersonDto getOne(@PathVariable Long id) {
        return personService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<PersonDto> getMany(@RequestParam List<Long> ids) {
        return personService.getMany(ids);
    }

    @PostMapping
    public PersonDto create(@RequestBody PersonDto dto) {
        return personService.create(dto);
    }

    @PatchMapping("/{id}")
    public PersonDto patch(@PathVariable Long id, @RequestBody PersonDto patchNode) throws IOException {
        return personService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public PersonDto delete(@PathVariable Long id) {
        return personService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        personService.deleteMany(ids);
    }
}
