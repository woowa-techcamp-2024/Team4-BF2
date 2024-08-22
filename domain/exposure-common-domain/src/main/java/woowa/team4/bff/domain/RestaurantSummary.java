package woowa.team4.bff.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RestaurantSummary {

    private Long id;
    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private int minimumOrderAmount;
    private long reviewCount;
    private double rating;
    private String menus;

    public void updateReviewStatistics(Double rating, Long reviewCount) {
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}
