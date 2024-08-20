package woowa.team4.bff.cache.redis.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.interfaces.CacheService;

@Service
@RequiredArgsConstructor
public class CacheRedisService implements CacheService {

    private final RestaurantCacheService restaurantCacheService;
    private final ReviewStatisticsCacheService reviewStatisticsCacheService;

    @Override
    public List<RestaurantSummary> findByRestaurantIds(List<Long> ids) {
        return List.of();
    }
}
