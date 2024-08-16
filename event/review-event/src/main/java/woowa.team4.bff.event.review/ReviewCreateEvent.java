package woowa.team4.bff.event.review;

import woowa.team4.bff.event.Event;

public record ReviewCreateEvent(Long restaurantId, Double rating) implements Event {
}
