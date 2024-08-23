package woowa.team4.bff.api.client.cache.response;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheResponse {

    private long restaurantId;
    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private int minimumOrderAmount;
    private long reviewCount;
    private double rating;
    private String menus;
}
