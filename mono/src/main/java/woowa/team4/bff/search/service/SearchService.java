package woowa.team4.bff.search.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowa.team4.bff.restaurant.repository.RestaurantFinder;
import woowa.team4.bff.search.command.SearchRestaurantCommand;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.repository.RestaurantSearchResultRepository;
import woowa.team4.bff.search.repository.SearchEsRepository;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchEsRepository searchEsRepository;
    private final RestaurantFinder restaurantFinder;
    private final RestaurantSearchResultRepository restaurantSearchResultRepository;
    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    public List<RestaurantSearchResult> searchEs(SearchRestaurantCommand command) {
        return restaurantSearchResultRepository.findByRestaurantIdsAndDeliveryLocation(getRestaurantIdsInEs(command.keyword()), command.deliveryLocation());
    }

    private List<Long> getRestaurantIdsInEs(String keyword) {
        long start = System.currentTimeMillis();
        List<RestaurantSearch> restaurantSearches = searchEsRepository.findAllByRestaurantName(keyword);
        List<MenuSearch> menuSearches = searchEsRepository.findAllByMenuName(keyword);
        log.info("Method 'getRestaurantIds' execution time: " + (System.currentTimeMillis() - start));
        List<Long> ids = new ArrayList<>();
        List<Long> results = restaurantSearches.stream().map(RestaurantSearch::getRestaurantId).toList();
        ids.addAll(results);
        log.info("hit-restaurantIds-by-restaurantName "+ ids);
        results = menuSearches.stream().map(MenuSearch::getRestaurantId).toList();
        log.info("hit-restaurantIds-by-menuName");
        ids.addAll(results);
        return ids;
    }


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
