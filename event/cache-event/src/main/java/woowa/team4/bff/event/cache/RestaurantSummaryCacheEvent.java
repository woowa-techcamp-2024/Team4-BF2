package woowa.team4.bff.event.cache;

import java.util.List;
import woowa.team4.bff.event.Event;

public record RestaurantSummaryCacheEvent(List<Long> restaurantIds) implements Event {
}
