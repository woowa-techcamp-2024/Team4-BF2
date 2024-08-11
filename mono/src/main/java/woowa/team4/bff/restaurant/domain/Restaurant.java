package woowa.team4.bff.restaurant.domain;

import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.restaurant.controller.dto.request.CreateRestaurantRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Restaurant {

    private final Long id;
    private final String name;
    private final String phone;
    private final String address;
    private final Coordinate location;
    private final String introduction;
    private final String image;
    private final String operatingTime;
    private final String closedDays;
    private final Integer minimumOrderAmount;
    private final Long businessId;
    private final UUID uuid;
    private final LocalDateTime createdAt;

    @Builder
    public Restaurant(Long id, String name, String phone, String address, Coordinate location, String introduction, String image, String operatingTime, String closedDays, Integer minimumOrderAmount, Long businessId, UUID uuid, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.location = location;
        this.introduction = introduction;
        this.image = image;
        this.operatingTime = operatingTime;
        this.closedDays = closedDays;
        this.minimumOrderAmount = minimumOrderAmount;
        this.businessId = businessId;
        this.uuid = uuid;
        this.createdAt = createdAt;
    }

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
}
