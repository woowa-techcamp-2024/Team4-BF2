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
    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private Long reviewCount;
    private Double rating;
}
