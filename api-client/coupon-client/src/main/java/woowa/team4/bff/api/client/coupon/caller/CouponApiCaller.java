package woowa.team4.bff.api.client.coupon.caller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.coupon.config.CouponClientConfiguration;
import woowa.team4.bff.api.client.coupon.request.CouponRequest;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Component
@RequiredArgsConstructor
public class CouponApiCaller {

    private final CouponClientConfiguration couponClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;
    private final AsyncClientApiCaller asyncClientApiCaller;
    private final WebClientCaller webClientCaller;

    public List<CouponResponse> send(CouponRequest couponRequest) {
        return syncClientApiCaller.post(couponClientConfiguration.getUrl(),
                couponRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public CompletableFuture<List<CouponResponse>> sendAsyncCompletableFuture(
            CouponRequest couponRequest) {
        return asyncClientApiCaller.post(couponClientConfiguration.getUrl(),
                couponRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public Mono<List<CouponResponse>> sendAsyncMono(CouponRequest couponRequest) {
        return webClientCaller.post(
                couponClientConfiguration.getUrl(),
                couponRequest,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
