package woowa.team4.bff.restaurant.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

@Component
@RequiredArgsConstructor
public class RestaurantRegistrant {

    private final RestaurantRepository restaurantRepository;

    public Restaurant register(Restaurant restaurant) {
        RestaurantEntity entity = restaurant.toEntity();
        RestaurantEntity savedEntity = restaurantRepository.save(entity);
        return Restaurant.fromEntity(savedEntity);
    }
}
