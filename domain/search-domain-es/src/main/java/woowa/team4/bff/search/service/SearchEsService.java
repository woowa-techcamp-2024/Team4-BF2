package woowa.team4.bff.search.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.repository.SearchEsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchEsService implements SearchService {
    private final SearchEsRepository searchEsRepository;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber) {
        List<RestaurantSearch> restaurantSearches = searchEsRepository.findAllByRestaurantName(keyword);
        List<MenuSearch> menuSearches = searchEsRepository.findAllByMenuName(keyword);

        List<Long> ids = new ArrayList<>();
        List<Long> results = restaurantSearches.stream().map(RestaurantSearch::getRestaurantId).toList();
        ids.addAll(results);
        results = menuSearches.stream().map(MenuSearch::getRestaurantId).toList();
        ids.addAll(results);
        return ids;
    }

    @Override
    public List<RestaurantSummary> findRestaurantSummaryByKeywordAndDeliveryLocation(String keyword,
                                                                                     String deliveryLocation,
                                                                                     Integer pageNumber) {
        return List.of();
    }
}
