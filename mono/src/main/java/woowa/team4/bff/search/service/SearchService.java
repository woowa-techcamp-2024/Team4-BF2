package woowa.team4.bff.search.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.search.domain.Restaurant;
import woowa.team4.bff.search.repository.SearchRepository;
import woowa.team4.bff.search.service.command.CreateRestaurantCommand;
import woowa.team4.bff.search.service.command.SearchRestaurantCommand;
import woowa.team4.bff.search.service.command.UpdateRestaurantCommand;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public String addRestaurant(CreateRestaurantCommand command) {
        Restaurant restaurant = Restaurant.builder()
                .restaurantId(command.restaurantId())
                .restaurantName(command.restaurantName())
                .build();
        return searchRepository.save(restaurant);
    }

    public String updateRestaurant(UpdateRestaurantCommand command) {
        Restaurant restaurant = searchRepository.findByRestaurantId(command.restaurantId());
        return searchRepository.save(restaurant);
    }

    public List<Restaurant> search(SearchRestaurantCommand command){
        return searchRepository.findAllByRestaurantName(command.restaurantName());
    }
}
