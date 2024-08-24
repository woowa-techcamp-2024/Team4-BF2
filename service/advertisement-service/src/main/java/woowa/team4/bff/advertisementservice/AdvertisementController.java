package woowa.team4.bff.advertisementservice;

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
 * 광고 적용 유무 더미 API 소요시간: 50ms
 */
@RestController
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    private final Random random = new Random();

    @PostMapping
    public List<AdvertisementResponse> getAdvertisements(
            @RequestBody AdvertisementRequest request) {
        List<AdvertisementResponse> responses = new ArrayList<>();

        // 연산 작업 가정
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Long id : request.getIds()) {
            int rank = 0;
            responses.add(new AdvertisementResponse(id, ++rank, random.nextBoolean()));
        }

        return responses;
    }

    @Getter
    @Setter
    static class AdvertisementRequest {

        private List<Long> ids;
        private String keyword;
    }

    @Getter
    @Setter
    static class AdvertisementResponse {

        private long restaurantId;
        private int adRank;
        private boolean hasAdvertisement;

        public AdvertisementResponse(long restaurantId, int adRank, boolean hasAdvertisement) {
            this.restaurantId = restaurantId;
            this.hasAdvertisement = hasAdvertisement;
            this.adRank = Integer.MAX_VALUE;
            if (this.hasAdvertisement) {
                this.adRank = adRank;
            }
        }
    }
}
