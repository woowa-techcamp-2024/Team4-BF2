package woowa.team4.bff.exposure.caller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
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
public class AsyncExternalApiCaller {

    private final DeliveryTimeApiCaller deliveryTimeApiCaller;
    private final CouponApiCaller couponApiCaller;
    private final AdvertisementApiCaller advertisementApiCaller;

    public CompletableFuture<List<DeliveryTimeResponse>> getDeliveryTime(List<Long> restaurantIds) {
        return deliveryTimeApiCaller.sendAsyncCompletableFuture(
                new DeliveryTimeRequest(restaurantIds));
    }

    public Mono<List<DeliveryTimeResponse>> getDeliveryTimeWebFlux(List<Long> restaurantIds) {
        return deliveryTimeApiCaller.sendAsyncMono(new DeliveryTimeRequest(restaurantIds));
    }

    public CompletableFuture<List<CouponResponse>> getCoupon(List<Long> restaurantIds) {
        return couponApiCaller.sendAsyncCompletableFuture(
                new CouponRequest(restaurantIds));
    }

    public Mono<List<CouponResponse>> getCouponWebFlux(List<Long> restaurantIds) {
        return couponApiCaller.sendAsyncMono(new CouponRequest(restaurantIds));
    }

    public CompletableFuture<List<AdvertisementResponse>> getAdvertisement(List<Long> restaurantIds,
            String keyword) {
        return advertisementApiCaller.sendAsyncCompletableFuture(
                new AdvertisementRequest(restaurantIds, keyword));
    }

    public Mono<List<AdvertisementResponse>> getAdvertisementWebFlux(List<Long> restaurantIds,
            String keyword) {
        return advertisementApiCaller.sendAsyncMono(
                new AdvertisementRequest(restaurantIds, keyword));
    }
}
