package woowa.team4.bff.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantSummary {
    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private Long reviewCount;
    private Double rating;
    private String menus;

    @Builder
    public RestaurantSummary(UUID restaurantUuid, String restaurantName, String restaurantThumbnailUrl,
                             Long reviewCount,
                             Double rating, String menus) {
        this.restaurantUuid = restaurantUuid;
        this.restaurantName = restaurantName;
        this.restaurantThumbnailUrl = restaurantThumbnailUrl;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.menus = menus;
    }
}
