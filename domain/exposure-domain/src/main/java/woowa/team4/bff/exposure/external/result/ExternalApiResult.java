package woowa.team4.bff.exposure.external.result;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalApiResult {

    private long restaurantId;

    private UUID restaurantUuid;
    private String restaurantName;
    private String restaurantThumbnailUrl;
    private int minimumOrderAmount;
    private long reviewCount;
    private double rating;
    private String menus;

    private int adRank;
    private boolean hasAdvertisement;
    private boolean hasCoupon;
    private String couponName;
    private int min;
    private int max;
}
