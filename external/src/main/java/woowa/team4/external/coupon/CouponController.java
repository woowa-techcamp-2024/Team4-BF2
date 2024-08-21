package woowa.team4.external.coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 쿠폰 적용 유무 더미 API 소요시간: 50ms
 */
@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final Random random = new Random();

    @PostMapping
    public List<CouponResponse> getCoupons(@RequestBody List<Long> ids) {
        List<CouponResponse> responses = new ArrayList<>();

        // 연산 작업 가정
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Long id : ids) {
            responses.add(new CouponResponse(id, random.nextBoolean()));
        }

        return responses;
    }

    @Getter
    @Setter
    static class CouponResponse {

        private long restaurantId;
        private boolean hasCoupon;
        private String couponName;

        public CouponResponse(long restaurantId, boolean hasCoupon) {
            this.restaurantId = restaurantId;
            this.hasCoupon = hasCoupon;
            if (this.hasCoupon) {
                this.couponName = "3,000원 쿠폰";
            }
        }
    }
}
