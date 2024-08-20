package woowa.team4.bff.search.domain;

import java.util.UUID;
import lombok.Getter;

@Getter
public class RestaurantSearchResult {

    private UUID restaurantUuid;
    private String restaurantName;
    private Integer minimumOrderAmount;
    private Double averageRating;
    private Long reviewCount;
    private String menuNames;

    public RestaurantSearchResult(UUID restaurantUuid, String restaurantName, Integer minimumOrderAmount,
                                  Double averageRating, Long reviewCount, String menuNames) {
        this.restaurantUuid = restaurantUuid;
        this.restaurantName = restaurantName;
        this.minimumOrderAmount = minimumOrderAmount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.menuNames = menuNames;
    }
}
