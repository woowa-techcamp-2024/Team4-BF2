package woowa.team4.bff.restaurant.event;

import java.util.UUID;

public record RestaurantRegistrationEvent(UUID uuid) implements Event {
}
