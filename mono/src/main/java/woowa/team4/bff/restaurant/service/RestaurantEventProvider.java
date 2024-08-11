package woowa.team4.bff.restaurant.service;

import org.springframework.stereotype.Component;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.event.EventPublisher;

@Component
public class RestaurantEventProvider {

    private final EventPublisher eventPublisher;

    public RestaurantEventProvider(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRegistrationEvent(Restaurant restaurant) {
        // TODO 가게 등록 이벤트 생성•발행
    }
}
