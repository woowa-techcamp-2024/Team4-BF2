package woowa.team4.bff.restaurant.controller.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import woowa.team4.bff.restaurant.command.RestaurantRegistrationCommand;

public record RegisterRestaurantRequest(@NotBlank String name, @NotBlank String phone, @NotBlank String address,
                                        @NotBlank String introduction, String image, @NotBlank String operatingTime,
                                        @NotBlank String closedDays, @PositiveOrZero int minimumOrderAmount, @NotBlank String deliveryLocation) {

    public RestaurantRegistrationCommand toCommand() {
        return RestaurantRegistrationCommand.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .introduction(introduction)
                .image(image)
                .operatingTime(operatingTime)
                .closedDays(closedDays)
                .minimumOrderAmount(minimumOrderAmount)
                .deliveryLocation(deliveryLocation)
                .build();
    }
}
