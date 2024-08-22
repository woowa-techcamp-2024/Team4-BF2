package woowa.team4.bff.api.client.coupon.caller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import woowa.team4.bff.api.client.coupon.config.CouponClientConfiguration;
import woowa.team4.bff.api.client.coupon.request.CouponRequest;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponApiCaller {

    private final CouponClientConfiguration couponClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;

    public List<CouponResponse> send(CouponRequest couponRequest) {
        return syncClientApiCaller.post(couponClientConfiguration.getUrl(),
                couponRequest,
                new ParameterizedTypeReference<>() {
                });
    }
}
