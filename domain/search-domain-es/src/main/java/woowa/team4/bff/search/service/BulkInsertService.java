package woowa.team4.bff.search.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.search.document.MenuSearchDocument;
import woowa.team4.bff.search.document.RestaurantSearchDocument;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.repository.MenuSearchRepository;
import woowa.team4.bff.search.repository.RestaurantSearchRepository;

@Service
@RequiredArgsConstructor
public class BulkInsertService {
    private final RestaurantSearchRepository restaurantSearchRepository;
    private final MenuSearchRepository menuSearchRepository;

    public void bulkInsertRestaurant(List<RestaurantSearch> restaurantSearchList){
        restaurantSearchRepository.saveAll(restaurantSearchList.stream().map(e -> RestaurantSearchDocument.builder()
                .restaurantId(e.getRestaurantId())
                .restaurantName(e.getRestaurantName())
                .build()).collect(Collectors.toList()));
    }

    public void bulkInsertMenu(List<MenuSearch> menuSearches){
        List<MenuSearchDocument> ret = menuSearches.stream().map(e -> MenuSearchDocument.builder()
                .menuId(e.getMenuId())
                .menuName(e.getMenuName())
                .restaurantId(e.getRestaurantId())
                .build()).toList();
        menuSearchRepository.saveAll(ret);
    }
}
