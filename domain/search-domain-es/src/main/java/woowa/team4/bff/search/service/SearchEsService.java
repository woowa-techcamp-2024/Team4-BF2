package woowa.team4.bff.search.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.repository.SearchEsRepository;

@Service
@RequiredArgsConstructor
public class SearchEsService {
    private final SearchEsRepository searchEsRepository;
    private final Logger log = LoggerFactory.getLogger(SearchEsService.class);

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
}
