package woowa.team4.bff.search.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.common.aop.MethodLogging;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.repository.RestaurantEntityRepository;
import woowa.team4.bff.search.repository.SearchRepository;
import woowa.team4.bff.search.service.command.CreateRestaurantSearchCommand;
import woowa.team4.bff.search.service.command.SearchRestaurantCommand;
import woowa.team4.bff.search.service.command.UpdateRestaurantSearchCommand;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final RestaurantEntityRepository restaurantEntityRepository;

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
    public List<RestaurantSearchResult> search(SearchRestaurantCommand command){
        return restaurantEntityRepository.findRestaurantSearchResults(getRestaurantIds(command.keyword()), command.deliveryLocation());
    }

    private List<Long> getRestaurantIds(String keyword){
        // ToDo: 비동기로 쏴도 괜찮을 까?
        List<RestaurantSearch> restaurantSearches =  searchRepository.findAllByRestaurantName(keyword);
        List<MenuSearch> menuSearches =  searchRepository.findAllByMenuName(keyword);
        List<Long> ids = new ArrayList<>();
        ids.addAll(restaurantSearches.stream().map(RestaurantSearch::getRestaurantId).toList());
        ids.addAll(menuSearches.stream().map(MenuSearch::getRestaurantId).toList());
        return ids;
    }
}
