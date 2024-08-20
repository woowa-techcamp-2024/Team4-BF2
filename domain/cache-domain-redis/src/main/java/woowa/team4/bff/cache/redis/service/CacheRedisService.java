package woowa.team4.bff.cache.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import woowa.team4.bff.cache.redis.repository.RestaurantReviewStatisticsRepository;
import woowa.team4.bff.cache.redis.utils.JsonConverter;
import woowa.team4.bff.cache.redis.utils.RedisKeyExtractor;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.interfaces.CacheService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CacheRedisService implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RestaurantReviewStatisticsRepository restaurantReviewStatisticsRepository;
    private final JsonConverter jsonConverter;

    @Override
    public List<RestaurantSummary> findByRestaurantIds(List<Long> ids) {
        return ids.stream()
                .map(this::findById)
                .filter(Objects::nonNull)
                .toList();
    }

    public RestaurantSummary findById(Long id) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String restaurantKey = RedisKeyExtractor.extractRestaurantKey(id);
        String json = valueOperations.get(restaurantKey);
        if (json == null) {
            RestaurantSummary restaurantSummary = restaurantReviewStatisticsRepository.findByRestaurantId(id);
            valueOperations.set(restaurantKey, jsonConverter.convert(restaurantSummary));
            return restaurantSummary;
        }
        return jsonConverter.convert(json, RestaurantSummary.class);
    }
}
