package woowa.team4.deliverytimeexternal;

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
 * 배달 시간 계산 더미 API 소요시간: 50ms min 범위 : 5 ~ 35 max : max + 15
 */
@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryTimeController {

    private final Random random = new Random();

    @PostMapping
    public List<DeliveryTimeResponse> getDelivery(@RequestBody DeliveryTimeRequest request) {
        List<DeliveryTimeResponse> responses = new ArrayList<>();

        // 연산 작업 가정
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Long id : request.getIds()) {
            responses.add(new DeliveryTimeResponse(id, random.nextInt(30) + 5));
        }
        return responses;
    }

    @Getter
    @Setter
    static class DeliveryTimeRequest {

        private List<Long> ids;
    }

    @Getter
    @Setter
    static class DeliveryTimeResponse {

        private long restaurantId;
        private int min;
        private int max;

        public DeliveryTimeResponse(long restaurantId, int min) {
            this.restaurantId = restaurantId;
            this.min = min;
            this.max = min + 15;
        }
    }
}
