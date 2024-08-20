package woowa.team4.bff.search.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.repository.RestaurantSearchResultRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRdbService implements SearchService {
    private final RestaurantSearchResultRepository restaurantSearchResultRepository;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation) {
        return restaurantSearchResultRepository.findIdsByKeywordAndDeliveryLocation(keyword, deliveryLocation);
    }

    @Override
    public List<RestaurantSummary> findRestaurantSummaryByKeywordAndDeliveryLocation(String keyword, String deliveryLocation) {
        List<RestaurantSearchResult> ret = restaurantSearchResultRepository.findByKeywordAndDeliveryLocation(keyword, deliveryLocation);
        log.info("[findRestaurantSummaryByKeywordAndDeliveryLocation]" + ret.toString());
        return ret
                .stream()
                .map(e -> RestaurantSummary.builder()
                        .restaurantUuid(e.getRestaurantUuid())
                        .restaurantName(e.getRestaurantName())
                        .rating(e.getAverageRating())
                        .reviewCount(e.getReviewCount())
                        .build())
                .toList();
    }
}
