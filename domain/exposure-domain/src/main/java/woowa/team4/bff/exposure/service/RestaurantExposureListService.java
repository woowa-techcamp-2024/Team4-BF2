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
    // private final CacheService cacheService;

    public List<RestaurantSummary> search(SearchCommand command){
//        List<Long> restaurantIds = searchService.findIdsByKeywordAndDeliveryLocation(command.keyword(), command.deliveryLocation());
//        return cacheService.findByRestaurantIds(restaurantIds);
        return List.of();
    }

    // ToDo: 실험 후 제거
    public List<RestaurantSummary> searchNoCache(SearchCommand command){
        return searchService.findRestaurantSummaryByKeywordAndDeliveryLocation(command.keyword(),
                command.deliveryLocation(), command.pageNumber());
    }
}
