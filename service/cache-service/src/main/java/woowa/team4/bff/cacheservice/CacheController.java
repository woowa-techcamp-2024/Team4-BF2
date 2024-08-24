package woowa.team4.bff.cacheservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.api.client.cache.request.CacheRequest;
import woowa.team4.bff.api.client.cache.response.CacheResponse;
import woowa.team4.bff.interfaces.CacheService;

@RequestMapping("/api/v1/cache")
@RestController
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("")
    public List<CacheResponse> findByRestaurantIds(CacheRequest request){
        return cacheService.findByRestaurantIds(request.getIds()).stream()
                .map(e -> CacheResponse.builder()
                        .restaurantId(e.getId())
                        .restaurantUuid(e.getRestaurantUuid())
                        .restaurantName(e.getRestaurantName())
                        .restaurantThumbnailUrl(e.getRestaurantThumbnailUrl())
                        .minimumOrderAmount(e.getMinimumOrderAmount())
                        .reviewCount(e.getReviewCount())
                        .rating(e.getRating())
                        .menus(e.getMenus())
                        .build())
                .toList();
    }
}
