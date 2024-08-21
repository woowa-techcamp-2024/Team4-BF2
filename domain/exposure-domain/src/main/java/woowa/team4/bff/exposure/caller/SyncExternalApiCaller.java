package woowa.team4.bff.exposure.caller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.api.client.coupon.caller.CouponApiCaller;
import woowa.team4.bff.api.client.coupon.request.CouponRequest;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.team4.bff.api.client.delivery.caller.DeliveryTimeApiCaller;
import woowa.team4.bff.api.client.delivery.request.DeliveryTimeRequest;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;

@Component
@RequiredArgsConstructor
public class SyncExternalApiCaller {

    private final DeliveryTimeApiCaller deliveryTimeApiCaller;
    private final CouponApiCaller couponApiCaller;

    public List<DeliveryTimeResponse> getDeliveryTime(List<Long> restaurantIds) {
        return deliveryTimeApiCaller.send(new DeliveryTimeRequest(restaurantIds));
    }

    public List<CouponResponse> getCoupon(List<Long> restaurantIds) {
        return couponApiCaller.send(new CouponRequest(restaurantIds));
    }
}
