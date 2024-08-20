package woowa.team4.bff.cache.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;
import woowa.team4.bff.restaurant.repository.RestaurantRepository;
import woowa.team4.bff.review.entity.ReviewStatisticsEntity;
import woowa.team4.bff.review.repository.ReviewStatisticsEntityRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantReviewStatisticsRepository {

    private final RestaurantRepository restaurantRepository;
    private final ReviewStatisticsEntityRepository reviewStatisticsEntityRepository;

    @Transactional(readOnly = true)
    public RestaurantSummary findByRestaurantId(Long id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        if (restaurantEntity.isEmpty()) {
            return null;
        }
        ReviewStatisticsEntity reviewStatisticsEntity = reviewStatisticsEntityRepository.findByRestaurantId(id)
                .orElseGet(() -> ReviewStatisticsEntity.builder()
                        .reviewCount(0L)
                        .averageRating(0.0)
                        .build());
        RestaurantEntity restaurant = restaurantEntity.get();
        return RestaurantSummary.builder()
                .restaurantUuid(restaurant.getUuid())
                .restaurantName(restaurant.getName())
                .restaurantThumbnailUrl("https://image.com")
                .minimumOrderAmount(restaurant.getMinimumOrderAmount())
                .reviewCount(reviewStatisticsEntity.getReviewCount())
                .rating(reviewStatisticsEntity.getAverageRating())
                .build();
    }
}
