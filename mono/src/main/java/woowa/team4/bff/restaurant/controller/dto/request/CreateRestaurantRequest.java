package woowa.team4.bff.restaurant.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import woowa.team4.bff.restaurant.domain.Coordinate;

public record CreateRestaurantRequest(@NotBlank String name, @NotBlank String phone, @NotBlank String address,
                                      @NotNull Coordinate location, @NotBlank String introduction, String image,
                                      @NotBlank String operatingTime, @NotBlank String closedDays,
                                      @PositiveOrZero int minimumOrderAmount) {
}
