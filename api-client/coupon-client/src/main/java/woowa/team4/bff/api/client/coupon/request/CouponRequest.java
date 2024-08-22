package woowa.team4.bff.api.client.coupon.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CouponRequest {

    private List<Long> ids;
}
