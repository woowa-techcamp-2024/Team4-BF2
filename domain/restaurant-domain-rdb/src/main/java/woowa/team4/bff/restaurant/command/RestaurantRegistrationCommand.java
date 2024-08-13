package woowa.team4.bff.restaurant.command;

import lombok.Builder;
import woowa.team4.bff.restaurant.domain.Restaurant;

@Builder
public record RestaurantRegistrationCommand(String name, String phone, String address, String introduction,
                                            String image, String operatingTime, String closedDays,
                                            int minimumOrderAmount, String deliveryLocation) {
}
