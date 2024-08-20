package woowa.team4.bff.cache.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.cache.redis.exception.JsonConvertException;
import woowa.team4.bff.cache.redis.repository.RestaurantReviewStatisticsRepository;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.event.reviewstatistics.ReviewStatisticsUpdateEvent;
import woowa.team4.bff.interfaces.CacheService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CacheRedisService implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RestaurantReviewStatisticsRepository restaurantReviewStatisticsRepository;

    @Override
    public List<RestaurantSummary> findByRestaurantIds(List<Long> ids) {
        return ids.stream()
                .map(this::findById)
                .filter(Objects::nonNull)
                .toList();
    }

    public RestaurantSummary findById(Long id) {
        String restaurantKey = getKey(id);
        String json = redisTemplate.opsForValue().get(restaurantKey);
        if (json == null) {
            RestaurantSummary restaurantSummary = restaurantReviewStatisticsRepository.findByRestaurantId(id);
            redisTemplate.opsForValue()
                    .set(restaurantKey, convert(restaurantSummary));
            return restaurantSummary;
        }
        return convert(json);
    }

    private String getKey(Long id) {
        return String.join(":", "restaurant", String.valueOf(id));
    }

    private RestaurantSummary convert(String json) {
        try {
            return objectMapper.readValue(json, RestaurantSummary.class);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e);
        }
    }

    private String convert(RestaurantSummary restaurantSummary) {
        try {
            return objectMapper.writeValueAsString(restaurantSummary);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e);
        }
    }

    @TransactionalEventListener
    public void handleReviewStatisticsUpdated(ReviewStatisticsUpdateEvent event) {
        Long restaurantId = event.restaurantId();
        String restaurantKey = getKey(restaurantId);
        String json = redisTemplate.opsForValue()
                .get(restaurantKey);
        if (json == null) {
            return;
        }
        RestaurantSummary restaurantSummary = convert(json);
        restaurantSummary.updateReviewStatistics(event.averageRating(), event.reviews());
        redisTemplate.opsForValue()
                .set(restaurantKey, convert(restaurantSummary));
    }
}
