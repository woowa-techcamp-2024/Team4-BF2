package woowa.team4.bff.event.reviewstatistics;

import woowa.team4.bff.event.Event;

public record ReviewStatisticsUpdateEvent(Long restaurantId, Double averageRating, Long reviews) implements Event {
}
