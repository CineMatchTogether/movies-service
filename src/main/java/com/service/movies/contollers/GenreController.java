package com.service.movies.contollers;

import com.fasterxml.jackson.databind.JsonNode;
import com.service.movies.models.dto.GenreDto;
import com.service.movies.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public PagedModel<GenreDto> getList(@ParameterObject Pageable pageable) {
        Page<GenreDto> genreDtos = genreService.getList(pageable);
        return new PagedModel<>(genreDtos);
    }

    @GetMapping("/{id}")
    public GenreDto getOne(@PathVariable Long id) {
        return genreService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<GenreDto> getMany(@RequestParam List<Long> ids) {
        return genreService.getMany(ids);
    }

    @PostMapping
    public GenreDto create(@RequestBody GenreDto dto) {
        return genreService.create(dto);
    }

    @PatchMapping("/{id}")
    public GenreDto patch(@PathVariable Long id, @RequestBody GenreDto patchNode) {
        return genreService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public GenreDto delete(@PathVariable Long id) {
        return genreService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        genreService.deleteMany(ids);
    }
}
