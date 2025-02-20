package com.service.movies.repositories;

import com.service.movies.models.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsByName(String name);

    Genre findByName(String name);
}