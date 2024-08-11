package woowa.team4.bff.restaurant.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public record RestaurantRegistrationEvent(UUID uuid) implements Event {
}
