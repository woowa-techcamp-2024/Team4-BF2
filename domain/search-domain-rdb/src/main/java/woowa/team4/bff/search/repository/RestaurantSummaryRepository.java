package woowa.team4.bff.search.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.search.aop.MethodLogging;

@Repository
@RequiredArgsConstructor
public class RestaurantSummaryRepository {
    private final RestaurantSummaryEntityRepository restaurantSummaryEntityRepository;

    @MethodLogging
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation){
        return restaurantSummaryEntityRepository.findIdsByKeywordAndDeliveryLocation(keyword, deliveryLocation);
    }

    @MethodLogging
    public List<RestaurantSummary> findByKeywordAndDeliveryLocation(String keyword, String deliveryLocation) {
        return restaurantSummaryEntityRepository.findByKeywordAndDeliveryLocation(keyword, deliveryLocation);
    }

    // id in ids
    @MethodLogging
    public List<RestaurantSummary> findByRestaurantIds(List<Long> restaurantIds){
        return restaurantSummaryEntityRepository.findByIds(restaurantIds);
    }
}
