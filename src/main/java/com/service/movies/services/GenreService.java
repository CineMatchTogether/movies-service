package com.service.movies.services;

import com.service.movies.mappers.GenreMapper;
import com.service.movies.models.dto.GenreDto;
import com.service.movies.models.entities.Genre;
import com.service.movies.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreMapper genreMapper;

    private final GenreRepository genreRepository;

    public Page<GenreDto> getList(Pageable pageable) {
        Page<Genre> genres = genreRepository.findAll(pageable);
        return genres.map(genreMapper::toDto);
    }

    public GenreDto getOne(Long id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        return genreMapper.toDto(genreOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<GenreDto> getMany(List<Long> ids) {
        List<Genre> genres = genreRepository.findAllById(ids);
        return genres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    public GenreDto create(GenreDto dto) {
        Genre genre = genreMapper.toEntity(dto);
        Genre resultGenre = genreRepository.save(genre);
        return genreMapper.toDto(resultGenre);
    }

    public GenreDto patch(Long id, GenreDto patchNode) {
        Genre genre = genreRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        genreMapper.partialUpdate(patchNode, genre);

        Genre resultGenre = genreRepository.save(genre);
        return genreMapper.toDto(resultGenre);
    }

    public GenreDto delete(Long id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre != null) {
            genreRepository.delete(genre);
        }
        return genreMapper.toDto(genre);
    }

    public void deleteMany(List<Long> ids) {
        genreRepository.deleteAllById(ids);
    }
}
