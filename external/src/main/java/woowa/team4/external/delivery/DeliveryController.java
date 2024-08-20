package woowa.team4.external.delivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배달비 계산 더미 API 소요시간: 50ms
 */
@RestController
@RequestMapping("/api/v1/deliveries/*")
public class DeliveryController {

    @GetMapping
    public String deliveries() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Delivery";
    }
}
