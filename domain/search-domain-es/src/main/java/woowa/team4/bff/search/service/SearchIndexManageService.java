package woowa.team4.bff.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.event.menu.MenuCreateEvent;
import woowa.team4.bff.event.menu.MenuUpdateEvent;
import woowa.team4.bff.event.restaurant.RestaurantCreateEvent;
import woowa.team4.bff.event.restaurant.RestaurantUpdateEvent;
import woowa.team4.bff.search.repository.SearchIndexManageRepository;

@Service
@RequiredArgsConstructor
public class SearchIndexManageService {

    private final SearchIndexManageRepository searchIndexManageRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long addRestaurant(RestaurantCreateEvent event) {
        RestaurantSearch restaurantSearch = RestaurantSearch.builder()
                .restaurantId(event.restaurantId())
                .restaurantName(event.restaurantName())
                .build();
        return searchIndexManageRepository.save(restaurantSearch);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long updateRestaurant(RestaurantUpdateEvent event) {
        RestaurantSearch restaurantSearch = searchIndexManageRepository.findByRestaurantId(event.restaurantId());
        restaurantSearch.update(event.restaurantName());
        return searchIndexManageRepository.save(restaurantSearch);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long addMenu(MenuCreateEvent event) {
        MenuSearch menuSearch = MenuSearch.builder()
                .menuId(event.menuId())
                .menuName(event.menuName())
                .restaurantId(event.restaurantId())
                .build();
        return searchIndexManageRepository.save(menuSearch);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long updateMenu(MenuUpdateEvent event) {
        MenuSearch menuSearch = searchIndexManageRepository.findByMenuId(event.menuId());
        menuSearch.update(event.menuName());
        return searchIndexManageRepository.save(menuSearch);
    }
}
