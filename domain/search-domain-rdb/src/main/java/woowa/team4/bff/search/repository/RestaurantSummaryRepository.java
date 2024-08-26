package woowa.team4.bff.search.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.search.aop.MethodLogging;

@Repository
@RequiredArgsConstructor
public class RestaurantSummaryRepository {
    private final int PAGE_SIZE = 25;
    private final RestaurantSummaryEntityRepository restaurantSummaryEntityRepository;

    @MethodLogging
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber){
        return restaurantSummaryEntityRepository.findIdsByKeywordAndDeliveryLocation(keyword, deliveryLocation, PageRequest.of(pageNumber, PAGE_SIZE));
    }

    @MethodLogging
    public List<RestaurantSummary> findByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber) {
        return restaurantSummaryEntityRepository.findByKeywordAndDeliveryLocation(keyword, deliveryLocation, PageRequest.of(pageNumber, PAGE_SIZE));
    }

    // id in ids
    @MethodLogging
    public List<RestaurantSummary> findByRestaurantIds(List<Long> restaurantIds){
        return restaurantSummaryEntityRepository.findByIds(restaurantIds);
    }

    public RestaurantSummary findByRestaurantId(Long restaurantIds){
        return restaurantSummaryEntityRepository.findByIds(List.of(restaurantIds)).getFirst();
    }
}
