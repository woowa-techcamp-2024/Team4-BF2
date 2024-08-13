package woowa.team4.bff.review.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.review.domain.Review;
import woowa.team4.bff.review.entity.ReviewEntity;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final ReviewEntityRepository reviewEntityRepository;

    public Review save(Review review) {
        ReviewEntity reviewEntity = toEntity(review);
        reviewEntityRepository.save(reviewEntity);
        return toDomain(reviewEntity);
    }

    private UUID extractUuid(String uuid) {
        String[] s = uuid.split("_");
        return UUID.fromString(s[s.length - 1]);
    }

    private String addPrefix(UUID uuid) {
        return "review_" + uuid.toString();
    }

    private ReviewEntity toEntity(Review review) {
        return ReviewEntity.builder()
                .restaurantUuid(extractUuid(review.getRestaurantUuid()))
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    private Review toDomain(ReviewEntity reviewEntity) {
        return Review.builder()
                .reviewUuId(addPrefix(reviewEntity.getUuid()))
                .restaurantUuid(addPrefix(reviewEntity.getRestaurantUuid()))
                .content(reviewEntity.getContent())
                .rating(reviewEntity.getRating())
                .build();
    }
}