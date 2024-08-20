package woowa.team4.external.advertisement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 광고 적용 유무 더미 API 소요시간: 50ms
 */
@RestController
@RequestMapping("/api/v1/advertisements/*")
public class AdvertisementController {

    @GetMapping
    public String advertisements() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Advertisements";
    }
}
