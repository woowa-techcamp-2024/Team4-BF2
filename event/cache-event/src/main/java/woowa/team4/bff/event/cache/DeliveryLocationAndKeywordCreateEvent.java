package woowa.team4.bff.event.cache;

import java.util.List;
import woowa.team4.bff.event.Event;

public record DeliveryLocationAndKeywordCreateEvent(String keyword, String deliveryLocation, List<Long> restaurantIds) implements
        Event {
}
