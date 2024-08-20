package woowa.team4.bff.interfaces;

import java.util.List;
import woowa.team4.bff.domain.RestaurantSummary;

public interface SearchService {
    List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation);
    List<RestaurantSummary> findRestaurantSummaryByKeywordAndDeliveryLocation(String keyword, String deliveryLocation);
}
