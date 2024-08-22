package woowa.team4.bff.cache.redis.utils;

public final class RedisKeyMaker {

    public static String makeRestaurantKey(Long id) {
        return String.join(":", "restaurant", String.valueOf(id));
    }

    public static String makeDeliveryLocation(String deliveryLocation){
        return String.join(":", "delivery_location", deliveryLocation);
    }
}
