package woowa.team4.bff.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.repository.SearchIndexManageRepository;
import woowa.team4.bff.search.service.command.CreateRestaurantSearchCommand;
import woowa.team4.bff.search.service.command.UpdateRestaurantSearchCommand;

@Service
@RequiredArgsConstructor
public class SearchIndexManageService {
    private final SearchIndexManageRepository searchIndexManageRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long addRestaurant(CreateRestaurantSearchCommand command) {
        RestaurantSearch restaurantSearch = RestaurantSearch.builder()
                .restaurantId(command.restaurantId())
                .restaurantName(command.restaurantName())
                .build();
        return searchIndexManageRepository.save(restaurantSearch);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Long updateRestaurant(UpdateRestaurantSearchCommand command) {
        RestaurantSearch restaurantSearch = searchIndexManageRepository.findByRestaurantId(command.restaurantId());
        restaurantSearch.update(command.restaurantName());
        return searchIndexManageRepository.save(restaurantSearch);
    }


}
