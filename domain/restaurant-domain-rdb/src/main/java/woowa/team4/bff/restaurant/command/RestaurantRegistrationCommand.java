package woowa.team4.bff.restaurant.command;

import lombok.Builder;
import woowa.team4.bff.restaurant.domain.Restaurant;

@Builder
public record RestaurantRegistrationCommand(String name, String phone, String address, String introduction,
                                            String image, String operatingTime, String closedDays,
                                            int minimumOrderAmount) {

    public Restaurant toDomain() {
        return Restaurant.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .introduction(introduction)
                .image(image)
                .operatingTime(operatingTime)
                .closedDays(closedDays)
                .minimumOrderAmount(minimumOrderAmount)
                .build();
    }
}
