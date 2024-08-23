package woowa.team4.bff.restaurantservice.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.restaurant.command.RestaurantRegistrationCommand;
import woowa.team4.bff.restaurantservice.restaurant.controller.create.RegisterRestaurantRequest;
import woowa.team4.bff.restaurantservice.restaurant.controller.create.RegisterRestaurantResponse;
import woowa.team4.bff.restaurant.service.RestaurantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiUtils.ApiResult<RegisterRestaurantResponse> registerRestaurant(@Validated @RequestBody RegisterRestaurantRequest request) {
        RestaurantRegistrationCommand command = request.toCommand();
        String restaurantId = restaurantService.registerRestaurant(command);
        return ApiUtils.success(new RegisterRestaurantResponse(restaurantId));
    }
}
