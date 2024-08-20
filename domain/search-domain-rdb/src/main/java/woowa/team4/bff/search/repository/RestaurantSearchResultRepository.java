package woowa.team4.bff.search.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.aop.MethodLogging;
import woowa.team4.bff.search.domain.RestaurantSearchResult;

@Repository
@RequiredArgsConstructor
public class RestaurantSearchResultRepository {
    private final RestaurantSearchResultEntityRepository restaurantSearchResultEntityRepository;

    @MethodLogging
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation){
        return restaurantSearchResultEntityRepository.findIdsByKeywordAndDeliveryLocation(keyword, deliveryLocation);
    }

    @MethodLogging
    public List<RestaurantSearchResult> findByKeywordAndDeliveryLocation(String keyword, String deliveryLocation) {
        return restaurantSearchResultEntityRepository.findByKeywordAndDeliveryLocation(keyword, deliveryLocation);
    }

    // id in ids
    @MethodLogging
    public List<RestaurantSearchResult> findByRestaurantIds(List<Long> restaurantIds){
        return restaurantSearchResultEntityRepository.findByIds(restaurantIds);
    }
}
