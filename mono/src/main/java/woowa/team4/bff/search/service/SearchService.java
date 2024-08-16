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
import woowa.team4.bff.search.repository.SearchRepository;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final RestaurantFinder restaurantFinder;
    private final RestaurantSearchResultRepository restaurantSearchResultRepository;
    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    public List<RestaurantSearchResult> searchEs(SearchRestaurantCommand command) {
        return restaurantSearchResultRepository.findByRestaurantIdsAndDeliveryLocation(getRestaurantIds(command.keyword()), command.deliveryLocation());
    }

    public List<RestaurantSearchResult> searchLike(SearchRestaurantCommand command) {
        long start = System.currentTimeMillis();
        List<Long> ids = restaurantFinder.findIdByKeyword(command.keyword());
        log.info("Method 'findIdByKeyword' execution time: " + (System.currentTimeMillis() - start));
        log.info("hit-restaurantIds-by-restaurantName "+ ids);
        return restaurantSearchResultRepository.findByRestaurantIdsAndDeliveryLocation(ids, command.deliveryLocation());
    }

    public List<Long> getRestaurantIds(String keyword) {
        long start = System.currentTimeMillis();
        List<RestaurantSearch> restaurantSearches = searchRepository.findAllByRestaurantName(keyword);
        // List<MenuSearch> menuSearches = searchRepository.findAllByMenuName(keyword);
        log.info("Method 'getRestaurantIds' execution time: " + (System.currentTimeMillis() - start));
        List<Long> ids = new ArrayList<>();
        List<Long> results = restaurantSearches.stream().map(RestaurantSearch::getRestaurantId).toList();
        ids.addAll(results);
        log.info("hit-restaurantIds-by-restaurantName "+ ids);
        // results = menuSearches.stream().map(MenuSearch::getRestaurantId).toList();
        // log.info("hit-restaurantIds-by-menuName");
        // ids.addAll(results);
        return ids;
    }
}
