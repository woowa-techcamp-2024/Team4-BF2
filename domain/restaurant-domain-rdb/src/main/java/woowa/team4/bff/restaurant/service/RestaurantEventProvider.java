package woowa.team4.bff.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.event.restaurant.RestaurantCreateEvent;
import woowa.team4.bff.publisher.EventPublisher;
import woowa.team4.bff.restaurant.domain.Restaurant;

@Component
@RequiredArgsConstructor
public class RestaurantEventProvider {

    private final EventPublisher eventPublisher;

    public void publishRegistrationEvent(Restaurant restaurant) {
        RestaurantCreateEvent event = new RestaurantCreateEvent(restaurant.getId(), restaurant.getName(), restaurant.getDeliveryLocation());
        eventPublisher.publish(event);
    }
}
