package woowa.team4.bff.api.client.coupon.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponResponse {

    private long restaurantId;
    private boolean hasCoupon;
    private String couponName;
}
