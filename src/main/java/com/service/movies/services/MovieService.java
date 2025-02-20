package com.service.movies.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.movies.filters.MovieFilter;
import com.service.movies.mappers.MovieMapper;
import com.service.movies.models.dto.MovieDto;
import com.service.movies.models.entities.Movie;
import com.service.movies.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;

    private final MovieRepository movieRepository;

    public Page<MovieDto> getList(MovieFilter filter, Pageable pageable) {
        Specification<Movie> spec = filter.toSpecification();
        Page<Movie> movies = movieRepository.findAll(spec, pageable);
        return movies.map(movieMapper::toDto);
    }

    public MovieDto getOne(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieMapper.toDto(movieOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<MovieDto> getMany(List<Long> ids) {
        List<Movie> movies = movieRepository.findAllById(ids);
        return movies.stream()
                .map(movieMapper::toDto)
                .toList();
    }

    public MovieDto create(MovieDto dto) {
        Movie movie = movieMapper.toEntity(dto);
        Movie resultMovie = movieRepository.save(movie);
        return movieMapper.toDto(resultMovie);
    }

    public MovieDto patch(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        movieMapper.partialUpdate(movieDto, movie);

        Movie resultMovie = movieRepository.save(movie);
        return movieMapper.toDto(resultMovie);
    }

    public MovieDto delete(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            movieRepository.delete(movie);
        }
        return movieMapper.toDto(movie);
    }

    public void deleteMany(List<Long> ids) {
        movieRepository.deleteAllById(ids);
    }

    @Transactional
    public void saveAll(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }
}
