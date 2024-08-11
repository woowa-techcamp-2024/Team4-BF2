package woowa.team4.bff.restaurant.service;

import org.springframework.stereotype.Service;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.domain.RestaurantRegistrant;

import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRegistrant restaurantRegistrant;
    private final RestaurantEventProvider restaurantEventProvider;

    public RestaurantService(RestaurantRegistrant restaurantRegistrant, RestaurantEventProvider restaurantEventProvider) {
        this.restaurantRegistrant = restaurantRegistrant;
        this.restaurantEventProvider = restaurantEventProvider;
    }

    public UUID registerRestaurant(Restaurant newRestaurant) {
        // TODO 가게에 대한 유효성 검사
        // TODO RDB에 가게 정보 저장
        // TODO 가게 등록 이벤트 발행(Elasticsearch에 변경 사항 반영)
        return null;
    }
}
