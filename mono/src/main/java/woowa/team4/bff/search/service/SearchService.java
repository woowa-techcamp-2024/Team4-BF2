package woowa.team4.bff.search.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowa.team4.bff.restaurant.repository.RestaurantFinder;
import woowa.team4.bff.search.command.SearchRestaurantCommand;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.repository.RestaurantSearchResultRepository;

@Service
@RequiredArgsConstructor
public class SearchService {
    //private final SearchEsService searchEsService;
    private final RestaurantFinder restaurantFinder;
    private final RestaurantSearchResultRepository restaurantSearchResultRepository;
    private final Logger log = LoggerFactory.getLogger(SearchService.class);

//    public List<RestaurantSearchResult> searchEs(SearchRestaurantCommand command) {
//        return restaurantSearchResultRepository.findByRestaurantIdsAndDeliveryLocation(getRestaurantIdsInEs(command.keyword()), command.deliveryLocation());
//    }
//
//    private List<Long> getRestaurantIdsInEs(String keyword) {
//        searchEsService.getRestaurantIdsInEs(keyword);
//    }

    public List<RestaurantSearchResult> searchLikeAndJoin(SearchRestaurantCommand command) {
        return restaurantSearchResultRepository.findByRestaurantNameAndDeliveryLocation(command.keyword(), command.deliveryLocation());
    }

    public List<RestaurantSearchResult> searchLikeAfterJoin(SearchRestaurantCommand command) {
        long start = System.currentTimeMillis();
        List<Long> ret = new ArrayList<>();
        List<Long> ids = restaurantSearchResultRepository.findIdsByKeywordAndDeliveryLocation(command.keyword(), command.deliveryLocation());
        log.info("Method 'findIdsByKeywordAndDeliveryLocation' execution time: " + (System.currentTimeMillis() - start));
        log.info("hit-restaurantIds-by-restaurantName "+ ids);
        ret.addAll(ids);
        return restaurantSearchResultRepository.findByRestaurantIds(ret);
    }
}
