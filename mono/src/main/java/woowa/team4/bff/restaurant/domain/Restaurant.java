package woowa.team4.bff.restaurant.domain;

import lombok.Builder;
import woowa.team4.bff.restaurant.controller.dto.request.CreateRestaurantRequest;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Restaurant(
        Long id, String name, String phone, String address, Coordinate location, String introduction,
        String image, String operatingTime, String closedDays, Integer minimumOrderAmount,
        Long businessId, UUID uuid, LocalDateTime createdAt
) {

    public static Restaurant newRestaurant(CreateRestaurantRequest request) {
        return Restaurant.builder()
                .name(request.name())
                .phone(request.phone())
                .address(request.address())
                .location(request.location())
                .introduction(request.introduction())
                .image(request.image())
                .operatingTime(request.operatingTime())
                .closedDays(request.closedDays())
                .minimumOrderAmount(request.minimumOrderAmount())
                .build();
    }

    public static Restaurant fromEntity(RestaurantEntity entity) {
        return Restaurant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .location(entity.getLocation())
                .introduction(entity.getIntroduction())
                .image(entity.getImage())
                .operatingTime(entity.getOperatingTime())
                .closedDays(entity.getClosedDays())
                .minimumOrderAmount(entity.getMinimumOrderAmount())
                .businessId(entity.getBusinessId())
                .uuid(entity.getUuid())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
