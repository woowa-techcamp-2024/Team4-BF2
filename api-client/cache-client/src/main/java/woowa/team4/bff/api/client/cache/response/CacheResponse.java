package woowa.team4.bff.api.client.cache.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheResponse {

    private Long restaurantId;
    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private int minimumOrderAmount;
    private long reviewCount;
    private double rating;
    private String menus;
}
