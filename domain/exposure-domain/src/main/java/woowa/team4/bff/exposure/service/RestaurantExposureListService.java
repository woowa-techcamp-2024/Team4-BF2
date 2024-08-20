package woowa.team4.bff.exposure.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.interfaces.CacheService;
import woowa.team4.bff.interfaces.SearchService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantExposureListService {
    private final SearchService searchService;
    private final CacheService cacheService;

    public List<RestaurantSummary> search(SearchCommand command){
        List<Long> restaurantIds = searchService.findByKeywordAndDeliveryLocation(command.keyword(), command.deliveryLocation());
        return cacheService.findByRestaurantIds(restaurantIds);
    }
}
