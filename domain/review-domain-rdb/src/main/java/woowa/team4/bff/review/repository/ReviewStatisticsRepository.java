package woowa.team4.bff.review.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.review.domain.ReviewStatistics;
import woowa.team4.bff.review.entity.ReviewStatisticsEntity;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewStatisticsRepository {

    private final ReviewStatisticsEntityRepository reviewStatisticsEntityRepository;

    public Optional<ReviewStatistics> findByRestaurantId(Long restaurantId) {
        Optional<ReviewStatisticsEntity> reviewStatisticsEntity = reviewStatisticsEntityRepository.findByRestaurantId(restaurantId);
        return reviewStatisticsEntity.map(this::toDomain);
    }

    public Optional<ReviewStatistics> findByRestaurantIdForUpdate(Long restaurantId) {
        Optional<ReviewStatisticsEntity> reviewStatisticsEntity = reviewStatisticsEntityRepository.findByRestaurantIdUsingPessimisticLock(restaurantId);
        return reviewStatisticsEntity.map(this::toDomain);
    }

    public ReviewStatistics save(ReviewStatistics reviewStatistics) {
        ReviewStatisticsEntity save = reviewStatisticsEntityRepository.save(toEntity(reviewStatistics));
        return toDomain(save);
    }

    public ReviewStatistics toDomain(ReviewStatisticsEntity reviewStatisticsEntity) {
        return ReviewStatistics.builder()
                .id(reviewStatisticsEntity.getId())
                .restaurantId(reviewStatisticsEntity.getRestaurantId())
                .reviewCount(reviewStatisticsEntity.getReviewCount())
                .averageRating(reviewStatisticsEntity.getAverageRating())
                .build();
    }

    public ReviewStatisticsEntity toEntity(ReviewStatistics reviewStatistics) {
        return ReviewStatisticsEntity.builder()
                .id(reviewStatistics.getId())
                .restaurantId(reviewStatistics.getRestaurantId())
                .reviewCount(reviewStatistics.getReviewCount())
                .averageRating(reviewStatistics.getAverageRating())
                .build();
    }

}
