package woowa.team4.external.coupon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 쿠폰 적용 유무 더미 API 소요시간: 50ms
 */
@RestController
@RequestMapping("/api/v1/coupons/*")
public class CouponController {

    @GetMapping
    public String coupons() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Coupon";
    }
}
