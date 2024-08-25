package woowa.team4.bff.cache.redis.service;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import woowa.team4.bff.cache.redis.repository.RestaurantReviewStatisticsRepository;
import woowa.team4.bff.cache.redis.utils.JsonConverter;
import woowa.team4.bff.cache.redis.utils.RedisKeyMaker;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.event.cache.RestaurantSummaryCacheEvent;
import woowa.team4.bff.interfaces.CacheService;

import java.util.List;
import java.util.Objects;
import woowa.team4.bff.publisher.EventPublisher;
import woowa.team4.bff.search.repository.RestaurantSummaryRepository;

@Service
@RequiredArgsConstructor
public class CacheRedisService implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonConverter jsonConverter;
    private final EventPublisher eventPublisher;
    private final RestaurantSummaryRepository restaurantSummaryRepository;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation) {
        String deliveryLocationKey = RedisKeyMaker.makeDeliveryLocation(deliveryLocation);
        String json = (String) redisTemplate.opsForHash().get(deliveryLocationKey, keyword);
        if(json == null){
            return null;
        }
        return jsonConverter.convert(json, new TypeReference <List<Long>>() {});
    }

    @Override
    public List<RestaurantSummary> findByRestaurantIds(List<Long> ids) {
        // 1. Redis keys 생성
        List<String> keys = ids.stream()
                .map(RedisKeyMaker::makeRestaurantKey)
                .collect(Collectors.toList());
        // 2. Redis에서 한 번에 모든 값 조회
        List<String> cachedValues = redisTemplate.opsForValue().multiGet(keys);
        // 3. 캐시 miss된 ID들 혹은 hit 데이터 추출
        List<Long> missingIds = new ArrayList<>();
        List<RestaurantSummary> results = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            String json = cachedValues.get(i);
            if (json != null) {
                results.add(jsonConverter.convert(json, RestaurantSummary.class));
            } else {
                missingIds.add(ids.get(i));
            }
        }

        // 4. miss 데이터 조회
        if (!missingIds.isEmpty()) {
            List<RestaurantSummary> dbResults = restaurantSummaryRepository.findByRestaurantIds(missingIds);
            results.addAll(dbResults);
            // 5. 캐시 이벤트 발행
            eventPublisher.publish(new RestaurantSummaryCacheEvent(missingIds));

        }
        return results;
    }
}
