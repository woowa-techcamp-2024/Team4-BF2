package woowa.team4.bff.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.common.utils.ApiUtils.ApiResult;
import woowa.team4.bff.restaurant.controller.dto.request.CreateRestaurantRequest;
import woowa.team4.bff.restaurant.controller.dto.response.RegisterRestaurantResponse;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.service.RestaurantService;

import java.util.UUID;

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResult<RegisterRestaurantResponse> registerRestaurant(@Validated @RequestBody CreateRestaurantRequest request) {
        Restaurant newRestaurant = Restaurant.newRestaurant(request);
        UUID restaurantId = restaurantService.registerRestaurant(newRestaurant);
        return ApiUtils.success(new RegisterRestaurantResponse(restaurantId));
    }
}
