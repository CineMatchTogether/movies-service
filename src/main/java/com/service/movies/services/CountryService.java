package com.service.movies.services;

import com.service.movies.mappers.CountryMapper;
import com.service.movies.models.dto.CountryDto;
import com.service.movies.models.entities.Country;
import com.service.movies.repositories.CountryRepository;
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
public class CountryService {

    private final CountryMapper countryMapper;

    private final CountryRepository countryRepository;

    public Page<CountryDto> getList(Pageable pageable) {
        Page<Country> countries = countryRepository.findAll(pageable);
        return countries.map(countryMapper::toDto);
    }

    public CountryDto getOne(Long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        return countryMapper.toDto(countryOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<CountryDto> getMany(List<Long> ids) {
        List<Country> countries = countryRepository.findAllById(ids);
        return countries.stream()
                .map(countryMapper::toDto)
                .toList();
    }

    public CountryDto create(CountryDto dto) {
        Country country = countryMapper.toEntity(dto);
        Country resultCountry = countryRepository.save(country);
        return countryMapper.toDto(resultCountry);
    }

    public CountryDto patch(Long id, CountryDto patchNode) {
        Country country = countryRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        countryMapper.partialUpdate(patchNode, country);

        Country resultCountry = countryRepository.save(country);
        return countryMapper.toDto(resultCountry);
    }

    public CountryDto delete(Long id) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country != null) {
            countryRepository.delete(country);
        }
        return countryMapper.toDto(country);
    }

    public void deleteMany(List<Long> ids) {
        countryRepository.deleteAllById(ids);
    }
}
