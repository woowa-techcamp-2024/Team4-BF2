package woowa.team4.bff.search.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
    private final Logger log = LoggerFactory.getLogger(SearchIndexManageService.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void addRestaurant(RestaurantCreateEvent event) {
        RestaurantSearch restaurantSearch = RestaurantSearch.builder()
                .restaurantId(event.restaurantId())
                .restaurantName(event.restaurantName())
                .build();
        log.info("[addRestaurant]: " + restaurantSearch);
        searchIndexManageRepository.save(restaurantSearch);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void updateRestaurant(RestaurantUpdateEvent event) {
        RestaurantSearch restaurantSearch = searchIndexManageRepository.findByRestaurantId(event.restaurantId());
        restaurantSearch.update(event.restaurantName());
        log.info("[updateRestaurant]: " + restaurantSearch);
        searchIndexManageRepository.save(restaurantSearch);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void addMenu(MenuCreateEvent event) {
        MenuSearch menuSearch = MenuSearch.builder()
                .menuId(event.menuId())
                .menuName(event.menuName())
                .restaurantId(event.restaurantId())
                .build();
        log.info("[addMenu]: " + menuSearch);
        searchIndexManageRepository.save(menuSearch);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void updateMenu(MenuUpdateEvent event) {
        MenuSearch menuSearch = searchIndexManageRepository.findByMenuId(event.menuId());
        menuSearch.update(event.menuName());
        log.info("[updateMenu]: " + menuSearch);
        searchIndexManageRepository.save(menuSearch);
    }
}
