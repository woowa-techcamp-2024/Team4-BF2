package woowa.team4.bff.exposure.external.caller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.api.client.advertisement.caller.AdvertisementApiCaller;
import woowa.team4.bff.api.client.advertisement.request.AdvertisementRequest;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
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
    private final AdvertisementApiCaller advertisementApiCaller;

    public List<DeliveryTimeResponse> getDeliveryTime(List<Long> restaurantIds) {
        return deliveryTimeApiCaller.sendSync(new DeliveryTimeRequest(restaurantIds));
    }

    public List<CouponResponse> getCoupon(List<Long> restaurantIds) {
        return couponApiCaller.send(new CouponRequest(restaurantIds));
    }

    public List<AdvertisementResponse> getAdvertisement(List<Long> restaurantIds, String keyword) {
        return advertisementApiCaller.send(new AdvertisementRequest(restaurantIds, keyword));
    }
}
