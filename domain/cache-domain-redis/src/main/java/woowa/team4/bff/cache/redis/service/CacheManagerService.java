package woowa.team4.bff.cache.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.cache.redis.utils.JsonConverter;
import woowa.team4.bff.cache.redis.utils.RedisKeyExtractor;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.event.reviewstatistics.ReviewStatisticsUpdateEvent;

@Service
@RequiredArgsConstructor
public class CacheManagerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonConverter jsonConverter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReviewStatisticsUpdated(ReviewStatisticsUpdateEvent event) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Long restaurantId = event.restaurantId();
        String restaurantKey = RedisKeyExtractor.extractRestaurantKey(restaurantId);
        String json = valueOperations.get(restaurantKey);
        if (json == null) {
            return;
        }
        RestaurantSummary restaurantSummary = jsonConverter.convert(json, RestaurantSummary.class);
        restaurantSummary.updateReviewStatistics(event.averageRating(), event.reviews());
        valueOperations.set(restaurantKey, jsonConverter.convert(restaurantSummary));
    }
}
