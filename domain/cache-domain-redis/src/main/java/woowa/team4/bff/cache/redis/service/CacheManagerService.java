package woowa.team4.bff.cache.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.cache.redis.repository.RestaurantReviewStatisticsRepository;
import woowa.team4.bff.cache.redis.utils.JsonConverter;
import woowa.team4.bff.cache.redis.utils.RedisKeyMaker;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.event.cache.DeliveryLocationAndKeywordCreateEvent;
import woowa.team4.bff.event.cache.RestaurantSummaryCacheEvent;
import woowa.team4.bff.event.reviewstatistics.ReviewStatisticsUpdateEvent;
import woowa.team4.bff.search.repository.RestaurantSummaryRepository;

@Service
@RequiredArgsConstructor
public class CacheManagerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonConverter jsonConverter;
    private final RestaurantSummaryRepository restaurantSummaryRepository;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReviewStatisticsUpdated(ReviewStatisticsUpdateEvent event) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Long restaurantId = event.restaurantId();
        String restaurantKey = RedisKeyMaker.makeRestaurantKey(restaurantId);
        String json = valueOperations.get(restaurantKey);
        if (json == null) {
            return;
        }
        RestaurantSummary restaurantSummary = jsonConverter.convert(json, RestaurantSummary.class);
        restaurantSummary.updateReviewStatistics(event.averageRating(), event.reviews());
        valueOperations.set(restaurantKey, jsonConverter.convert(restaurantSummary));
    }

    @Async
    @EventListener
    public void handleDeliveryLocationAndKeywordCreate(DeliveryLocationAndKeywordCreateEvent event) {
        String key = RedisKeyMaker.makeDeliveryLocation(event.deliveryLocation());
        redisTemplate.opsForHash().put(key, event.keyword(),
                jsonConverter.convert(event.restaurantIds()));
        redisTemplate.expire(
                key,
                1,
                TimeUnit.HOURS
        );
    }

    @Async
    @EventListener
    public void handle(RestaurantSummaryCacheEvent event){
        List<RestaurantSummary> restaurantSummaries = restaurantSummaryRepository.findByRestaurantIds(event.restaurantIds());
        bulkPutRestaurantSummaries(restaurantSummaries);
    }

    // redis 네트워크 통신 비용 때문에 한번의 통신에 여러 insert 보냄
    private void bulkPutRestaurantSummaries(List<RestaurantSummary> restaurantSummaries) {
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                // 트랜잭션 시작
                operations.multi();

                for (RestaurantSummary summary : restaurantSummaries) {
                    String key = RedisKeyMaker.makeRestaurantKey(summary.getId());
                    operations.opsForValue().set(key, jsonConverter.convert(summary));
                }
                // 명령어 전송
                return operations.exec();
            }
        });
    }
}
