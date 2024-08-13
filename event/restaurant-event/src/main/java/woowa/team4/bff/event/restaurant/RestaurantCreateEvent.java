package woowa.team4.bff.event.restaurant;

import woowa.team4.bff.event.Event;

public record RestaurantCreateEvent(Long restaurantId, String restaurantName) implements Event {
}
