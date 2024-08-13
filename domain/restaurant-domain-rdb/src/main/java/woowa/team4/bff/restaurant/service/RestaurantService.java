package woowa.team4.bff.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.restaurant.command.RestaurantRegistrationCommand;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.repository.RestaurantRegistrant;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantValidator restaurantValidator;
    private final RestaurantRegistrant restaurantRegistrant;
    private final RestaurantEventProvider restaurantEventProvider;

    public String registerRestaurant(RestaurantRegistrationCommand command) {
        Restaurant restaurant = Restaurant.builder()
                .name(command.name())
                .phone(command.phone())
                .address(command.address())
                .introduction(command.introduction())
                .image(command.image())
                .operatingTime(command.operatingTime())
                .closedDays(command.closedDays())
                .minimumOrderAmount(command.minimumOrderAmount())
                .deliveryLocation(command.deliveryLocation())
                .build();
        restaurantValidator.validateRestaurant(restaurant);
        Restaurant registered = restaurantRegistrant.register(restaurant);
        restaurantEventProvider.publishRegistrationEvent(registered);
        return registered.getUuid();
    }
}
