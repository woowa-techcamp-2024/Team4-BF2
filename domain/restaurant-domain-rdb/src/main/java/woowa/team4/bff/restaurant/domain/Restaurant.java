package woowa.team4.bff.restaurant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woowa.team4.bff.domain.common.utils.PrefixedUuidConverter;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class Restaurant {

    private final Long id;
    private final String name;
    private final String phone;
    private final String address;
    private final String introduction;
    private final String image;
    private final String operatingTime;
    private final String closedDays;
    private final Integer minimumOrderAmount;
    private final String uuid;
    private final String deliveryLocation;
    private final LocalDateTime createdAt;

    public static Restaurant fromEntity(RestaurantEntity entity) {
        return Restaurant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .introduction(entity.getIntroduction())
                .image(entity.getImage())
                .operatingTime(entity.getOperatingTime())
                .closedDays(entity.getClosedDays())
                .minimumOrderAmount(entity.getMinimumOrderAmount())
                .uuid(PrefixedUuidConverter.addPrefix("restaurant", entity.getUuid()))
                .deliveryLocation(entity.getDeliveryLocation())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public RestaurantEntity toEntity() {
        return RestaurantEntity.builder()
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
