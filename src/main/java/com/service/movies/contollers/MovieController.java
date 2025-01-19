package com.service.movies.contollers;

import com.service.movies.filters.MovieFilter;
import com.service.movies.models.dto.MovieDto;
import com.service.movies.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public PagedModel<MovieDto> getList(@ParameterObject @ModelAttribute MovieFilter filter, @ParameterObject Pageable pageable) {
        Page<MovieDto> movieDtos = movieService.getList(filter, pageable);
        return new PagedModel<>(movieDtos);
    }

    @GetMapping("/{id}")
    public MovieDto getOne(@PathVariable Long id) {
        return movieService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<MovieDto> getMany(@RequestParam List<Long> ids) {
        return movieService.getMany(ids);
    }

    @PostMapping
    public MovieDto create(@RequestBody MovieDto dto) {
        return movieService.create(dto);
    }

    @PatchMapping("/{id}")
    public MovieDto patch(@PathVariable Long id, @RequestBody MovieDto patchNode) {
        return movieService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    public MovieDto delete(@PathVariable Long id) {
        return movieService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        movieService.deleteMany(ids);
    }
}
