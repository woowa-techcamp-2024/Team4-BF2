package woowa.team4.bff.search.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.domain.RestaurantSearchResult;

@Repository
@RequiredArgsConstructor
public class RestaurantSearchResultRepository {
    private final RestaurantEntityRepository restaurantEntityRepository;

    public List<RestaurantSearchResult> findByRestaurantIdsAndDeliveryLocation(List<Long> restaurantIds, String deliveryLocation){
        return restaurantEntityRepository.findRestaurantSearchResults(restaurantIds, deliveryLocation);
    }
}
