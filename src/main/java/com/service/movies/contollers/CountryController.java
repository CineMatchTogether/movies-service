package com.service.movies.contollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.service.movies.models.dto.CountryDto;
import com.service.movies.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public PagedModel<CountryDto> getList(@ParameterObject Pageable pageable) {
        Page<CountryDto> countryDtos = countryService.getList(pageable);
        return new PagedModel<>(countryDtos);
    }

    @GetMapping("/{id}")
    public CountryDto getOne(@PathVariable Long id) {
        return countryService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<CountryDto> getMany(@RequestParam List<Long> ids) {
        return countryService.getMany(ids);
    }

    @PostMapping
    public CountryDto create(@RequestBody CountryDto dto) {
        return countryService.create(dto);
    }

    @PatchMapping("/{id}")
    public CountryDto patch(@PathVariable Long id, @RequestBody CountryDto patchNode) {
        return countryService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public CountryDto delete(@PathVariable Long id) {
        return countryService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        countryService.deleteMany(ids);
    }
}
