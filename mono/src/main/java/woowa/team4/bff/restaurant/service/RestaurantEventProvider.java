package woowa.team4.bff.restaurant.service;

import org.springframework.stereotype.Component;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.event.EventPublisher;
import woowa.team4.bff.restaurant.event.RestaurantRegistrationEvent;

@Component
public class RestaurantEventProvider {

    private final EventPublisher eventPublisher;

    public RestaurantEventProvider(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRegistrationEvent(Restaurant restaurant) {
        RestaurantRegistrationEvent event = new RestaurantRegistrationEvent(restaurant.uuid());
        eventPublisher.publish(event);
    }
}
