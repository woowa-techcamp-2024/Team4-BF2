package woowa.team4.external.delivery;

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
 * 배달비 계산 더미 API 소요시간: 50ms start 범위 : 5 ~ 35 end : start + 15
 */
@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final Random random = new Random();

    @PostMapping
    public List<DeliveryResponse> getDelivery(@RequestBody List<Long> ids) {
        List<DeliveryResponse> responses = new ArrayList<>();

        // 연산 작업 가정
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Long id : ids) {
            responses.add(new DeliveryResponse(id, random.nextInt(30) + 5));
        }
        return responses;
    }

    @Getter
    @Setter
    static class DeliveryResponse {

        private long restaurantId;
        private int start;
        private int end;

        public DeliveryResponse(long restaurantId, int start) {
            this.restaurantId = restaurantId;
            this.start = start;
            this.end = start + 15;
        }
    }
}
