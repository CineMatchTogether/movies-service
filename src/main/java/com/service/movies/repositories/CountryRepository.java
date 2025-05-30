package com.service.movies.repositories;

import com.service.movies.models.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {

    boolean existsByName(String name);

    Country findByName(String name);
}