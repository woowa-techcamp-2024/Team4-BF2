package woowa.team4.bff.review.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.domain.common.utils.PrefixedUuidConverter;
import woowa.team4.bff.review.domain.Review;
import woowa.team4.bff.review.entity.ReviewEntity;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepository {

    private final ReviewEntityRepository reviewEntityRepository;

    public Review save(Review review) {
        ReviewEntity reviewEntity = toEntity(review);
        ReviewEntity save = reviewEntityRepository.save(reviewEntity);
        log.info("[ReviewEntity Save], {}", save);
        return toDomain(save);
    }

    private ReviewEntity toEntity(Review review) {
        return ReviewEntity.builder()
                .restaurantId(review.getRestaurantId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    private Review toDomain(ReviewEntity reviewEntity) {
        return Review.builder()
                .id(reviewEntity.getId())
                .reviewUuId(PrefixedUuidConverter.addPrefix("review", reviewEntity.getUuid()))
                .restaurantId(reviewEntity.getRestaurantId())
                .content(reviewEntity.getContent())
                .rating(reviewEntity.getRating())
                .build();
    }
}