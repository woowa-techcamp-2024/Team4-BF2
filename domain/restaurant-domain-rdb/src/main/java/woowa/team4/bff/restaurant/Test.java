package woowa.team4.bff.restaurant;

import woowa.team4.bff.event.restaurant.RestaurantCreateEvent;
import woowa.team4.bff.publisher.EventPublisher;

public class Test {

    private final EventPublisher eventPublisher;

    public Test(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void test() {
        eventPublisher.publish(new RestaurantCreateEvent(1L, "sdf"));
    }
}
