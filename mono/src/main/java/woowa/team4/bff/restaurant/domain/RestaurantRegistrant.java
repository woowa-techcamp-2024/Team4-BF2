package woowa.team4.bff.restaurant.domain;

import org.springframework.stereotype.Component;
import woowa.team4.bff.restaurant.repository.RestaurantRepository;

@Component
public class RestaurantRegistrant {

    private final RestaurantRepository restaurantRepository;

    public RestaurantRegistrant(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant register(Restaurant restaurant) {
        // TODO entity로 변환 후 RDB에 저장
        return null;
    }
}
