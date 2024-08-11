package woowa.team4.bff.restaurant.domain;

import org.springframework.stereotype.Component;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;
import woowa.team4.bff.restaurant.repository.RestaurantRepository;

@Component
public class RestaurantRegistrant {

    private final RestaurantRepository restaurantRepository;

    public RestaurantRegistrant(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant register(Restaurant restaurant) {
        RestaurantEntity entity = RestaurantEntity.builder()
                .name(restaurant.name())
                .address(restaurant.address())
                .phone(restaurant.phone())
                .introduction(restaurant.introduction())
                .image(restaurant.image())
                .location(restaurant.location())
                .operatingTime(restaurant.operatingTime())
                .closedDays(restaurant.closedDays())
                .minimumOrderAmount(restaurant.minimumOrderAmount())
                .businessId(restaurant.businessId())
                .build();
        RestaurantEntity savedEntity = restaurantRepository.save(entity);
        return Restaurant.fromEntity(savedEntity);
    }
}
