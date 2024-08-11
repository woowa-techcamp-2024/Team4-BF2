package woowa.team4.bff.restaurant.service;

import org.springframework.stereotype.Service;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.domain.RestaurantRegistrant;

@Service
public class RestaurantService {

    private final RestaurantValidator restaurantValidator;
    private final RestaurantRegistrant restaurantRegistrant;
    private final RestaurantEventProvider restaurantEventProvider;

    public RestaurantService(RestaurantValidator restaurantValidator, RestaurantRegistrant restaurantRegistrant, RestaurantEventProvider restaurantEventProvider) {
        this.restaurantValidator = restaurantValidator;
        this.restaurantRegistrant = restaurantRegistrant;
        this.restaurantEventProvider = restaurantEventProvider;
    }

    public String registerRestaurant(Restaurant newRestaurant) {
        restaurantValidator.validateRestaurant(newRestaurant);
        Restaurant registered = restaurantRegistrant.register(newRestaurant);
        restaurantEventProvider.publishRegistrationEvent(registered);
        return registered.uuid();
    }
}
