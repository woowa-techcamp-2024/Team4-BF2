package woowa.team4.bff.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.event.menu.MenuCreateEvent;
import woowa.team4.bff.event.menu.MenuUpdateEvent;
import woowa.team4.bff.event.restaurant.RestaurantCreateEvent;
import woowa.team4.bff.event.restaurant.RestaurantUpdateEvent;
import woowa.team4.bff.search.domain.RestaurantMenusSearch;
import woowa.team4.bff.search.domain.RestaurantMenusSearch.Menu;
import woowa.team4.bff.search.repository.RestaurantMenusRepository;

@Service
@RequiredArgsConstructor
public class ElasticsearchIndexRegenerator {

    private final RestaurantMenusRepository restaurantMenusRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void addRestaurant(RestaurantCreateEvent event) {
        RestaurantMenusSearch restaurantMenus = RestaurantMenusSearch.builder()
                .restaurantName(event.restaurantName())
                .deliveryLocation(event.deliveryLocation())
                .restaurantId(event.restaurantId())
                .build();
        restaurantMenusRepository.save(restaurantMenus);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void updateRestaurant(RestaurantUpdateEvent event) {
        restaurantMenusRepository.findByRestaurantId(event.restaurantId())
                .ifPresent(search -> {
                    search.updateRestaurantName(event.restaurantName());
                    restaurantMenusRepository.save(search);
                });
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void addMenu(MenuCreateEvent event) {
        restaurantMenusRepository.findByRestaurantId(event.restaurantId())
                .ifPresent(search -> {
                    Menu menu = Menu.builder()
                            .menuName(event.menuName())
                            .menuId(event.menuId())
                            .build();
                    search.addMenu(menu);
                    restaurantMenusRepository.save(search);
                });
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void updateMenu(MenuUpdateEvent event) {
        restaurantMenusRepository.findByRestaurantId(event.restaurantId())
                .ifPresent(search -> {
                    search.getMenus()
                            .stream()
                            .filter(menu -> menu.getMenuId().equals(event.menuId()))
                            .findFirst()
                            .ifPresent(menu -> menu.updateMenuName(event.menuName()));
                    restaurantMenusRepository.save(search);
                });
    }
}