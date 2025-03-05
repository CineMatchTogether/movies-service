package com.service.movies.models.events;

public record PageMovieFetchedEvent(int pageNumber, long emptyDescriptions, long emptyShortDescriptions, long emptyPosters) {
}
