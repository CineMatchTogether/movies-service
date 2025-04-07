package com.service.movies.filters;

import com.service.movies.models.entities.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record MovieFilter(String genresNameContains, String nameContains, Integer yearGte, Integer yearLte,
                          Double kpRatingGte, Double kpRatingLte) {
    public Specification<Movie> toSpecification() {
        return Specification.where(genresNameContainsSpec())
                .and(nameContainsSpec())
                .and(yearGteSpec())
                .and(yearLteSpec())
                .and(kpRatingGteSpec())
                .and(kpRatingLteSpec());
    }

    private Specification<Movie> genresNameContainsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(genresNameContains)
                ? cb.like(cb.lower(root.get("genres").get("name")), "%" + genresNameContains.toLowerCase() + "%")
                : null);
    }

    private Specification<Movie> nameContainsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(nameContains)
                ? cb.like(cb.lower(root.get("enName")), "%" + nameContains.toLowerCase() + "%")
                : null);
    }

    private Specification<Movie> yearGteSpec() {
        return ((root, query, cb) -> yearGte != null
                ? cb.greaterThanOrEqualTo(root.get("year"), yearGte)
                : null);
    }

    private Specification<Movie> yearLteSpec() {
        return ((root, query, cb) -> yearLte != null
                ? cb.lessThanOrEqualTo(root.get("year"), yearLte)
                : null);
    }

    private Specification<Movie> kpRatingGteSpec() {
        return ((root, query, cb) -> kpRatingGte != null
                ? cb.greaterThanOrEqualTo(root.get("kpRating"), kpRatingGte)
                : null);
    }

    private Specification<Movie> kpRatingLteSpec() {
        return ((root, query, cb) -> kpRatingLte != null
                ? cb.lessThanOrEqualTo(root.get("kpRating"), kpRatingLte)
                : null);
    }
}