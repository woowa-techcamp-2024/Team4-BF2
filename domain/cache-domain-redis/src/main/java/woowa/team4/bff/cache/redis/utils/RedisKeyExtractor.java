package woowa.team4.bff.cache.redis.utils;

public final class RedisKeyExtractor {

    public static String extractRestaurantKey(Long id) {
        return String.join(":", "restaurant", String.valueOf(id));
    }
}
