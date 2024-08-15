package woowa.team4.bff.search.domain;

import java.util.UUID;
import lombok.Getter;

@Getter
public class RestaurantSearchResult {

    private UUID restaurantUuid;
    private String restaurantName;
    private Integer minimumOrderAmount;
    // ToDo: ReviewStatus Domain 기능 완성 후 추가
//    private Double rating;
//    private Integer reviewCount;
    private String menuNames;

    public RestaurantSearchResult(UUID restaurantUuid, String restaurantName, Integer minimumOrderAmount,
                                  String menuNames) {
        this.restaurantUuid = restaurantUuid;
        this.restaurantName = restaurantName;
        this.minimumOrderAmount = minimumOrderAmount;
        this.menuNames = menuNames;
    }
}
