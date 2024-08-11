package woowa.team4.bff.search.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.common.aop.MethodLogging;
import woowa.team4.bff.menu.repository.MenuRepository;
import woowa.team4.bff.restauarnt.domain.Restaurant;
import woowa.team4.bff.restauarnt.repository.RestaurantRepository;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.repository.SearchRepository;
import woowa.team4.bff.search.service.command.CreateRestaurantSearchCommand;
import woowa.team4.bff.search.service.command.SearchRestaurantCommand;
import woowa.team4.bff.search.service.command.UpdateRestaurantSearchCommand;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public String addRestaurant(CreateRestaurantSearchCommand command) {
        RestaurantSearch restaurantSearch = RestaurantSearch.builder()
                .restaurantId(command.restaurantId())
                .restaurantName(command.restaurantName())
                .build();
        return searchRepository.save(restaurantSearch);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public String updateRestaurant(UpdateRestaurantSearchCommand command) {
        RestaurantSearch restaurantSearch = searchRepository.findByRestaurantId(command.restaurantId());
        restaurantSearch.update(command.restaurantName());
        return searchRepository.save(restaurantSearch);
    }

    @MethodLogging
    public List<Restaurant> search(SearchRestaurantCommand command){
        // ToDo: 비동기로 쏴도 괜찮을 까?
        List<RestaurantSearch> restaurantSearches =  searchRepository.findAllByRestaurantName(command.keyword());
        List<MenuSearch> menuSearches =  searchRepository.findAllByMenuName(command.keyword());

        // restaurant id list, menu id list 가 주어질 때 join 으로 Restaurant & Menu 가져 오는 쿼리 짜야 할 듯
        // review 쪽도 join 해야 할까?
        restaurantRepository.findAllByIds(restaurantSearches.stream().map(RestaurantSearch::getRestaurantId).collect(Collectors.toList()),
                command.deliveryLocation());
        menuRepository.findAllByIds(menuSearches.stream().map(MenuSearch::getMenuId).collect(Collectors.toList()),
                command.deliveryLocation());
        return null;
    }
}
