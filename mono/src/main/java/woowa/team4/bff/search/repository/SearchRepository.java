package woowa.team4.bff.search.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.domain.Restaurant;

@Repository
@RequiredArgsConstructor
public class SearchRepository {
    private final RestaurantEsRepository restaurantEsRepository;
    private final RestaurantMapper restaurantMapper = RestaurantMapper.INSTANCE;

    public String save(Restaurant restaurant) {
        return restaurantMapper.toDomain(restaurantEsRepository.save(restaurantMapper.toDocument(restaurant)))
                .getRestaurantId();
    }

    public Restaurant findByRestaurantId(String restaurantId){
        return restaurantMapper.toDomain(restaurantEsRepository.findByRestaurantId(restaurantId));
    }

    public List<Restaurant> findAllByRestaurantName(String restaurantName) {
        return restaurantEsRepository.findAllByRestaurantNameContaining(restaurantName).stream()
                .map(restaurantMapper::toDomain)
                .collect(Collectors.toList());
    }
}
