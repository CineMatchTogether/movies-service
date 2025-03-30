package com.service.movies.models.events;

import java.util.List;

public record UserHistoryEvent(String pageInfo, List<Long> filmIds, boolean hasNextPage) {
}
