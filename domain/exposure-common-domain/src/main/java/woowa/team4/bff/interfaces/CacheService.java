package woowa.team4.bff.interfaces;

import java.util.List;
import woowa.team4.bff.domain.RestaurantSummary;

public interface CacheService {
    List<RestaurantSummary> findByRestaurantIds(List<Long> ids);
    List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation);
}
