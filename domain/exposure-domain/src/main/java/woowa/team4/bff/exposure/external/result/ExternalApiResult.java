package woowa.team4.bff.exposure.external.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExternalApiResult {

    private long restaurantId;
    private int adRank;
    private boolean hasAdvertisement;
    private boolean hasCoupon;
    private String couponName;
    private int min;
    private int max;
}
