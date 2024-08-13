package woowa.team4.bff.search.domain;

import java.util.UUID;
import lombok.Getter;

@Getter
public class RestaurantSearchResult {

    private UUID restaurantUuid;
    private String restaurantName;
    private Integer minimumOrderPrice;
    // ToDo: ReviewStatus Domain 기능 완성 후 추가
//    private Double rating;
//    private Integer reviewCount;
    private String menuName;

    public RestaurantSearchResult(UUID restaurantUuid, String restaurantName, Integer minimumOrderPrice,
                                  String menuName) {
        this.restaurantUuid = restaurantUuid;
        this.restaurantName = restaurantName;
        this.minimumOrderPrice = minimumOrderPrice;
        this.menuName = menuName;
    }
}
