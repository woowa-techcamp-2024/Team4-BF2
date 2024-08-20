package woowa.team4.bff.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import woowa.team4.bff.event.restaurant.RestaurantCreateEvent;
import woowa.team4.bff.event.review.ReviewCreateEvent;
import woowa.team4.bff.event.reviewstatistics.ReviewStatisticsUpdateEvent;
import woowa.team4.bff.publisher.EventPublisher;
import woowa.team4.bff.review.domain.ReviewStatistics;
import woowa.team4.bff.review.repository.ReviewStatisticsRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewStatisticsService {

    private final ReviewStatisticsRepository reviewStatisticsRepository;
    private final EventPublisher eventPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void updateReviewStatistics(ReviewCreateEvent event) {

        Optional<ReviewStatistics> optionalReviewStatistics = reviewStatisticsRepository.findByRestaurantId(event.restaurantId());
        // 해당 식당의 리뷰가 없을 경우 새로 생성
        if(optionalReviewStatistics.isEmpty()) {
            log.info("[no ReviewStatistics!]");
            return;
        }

        // 해당 식당의 리뷰가 있을 경우 업데이트 진행
        ReviewStatistics reviewStatistics = optionalReviewStatistics.get();
        ReviewStatistics savedReviewStatistic = reviewStatisticsRepository.save(updateReviewStatistics(reviewStatistics, event));
        eventPublisher.publish(new ReviewCreateEvent(event.restaurantId(), savedReviewStatistic.getAverageRating()));
        log.info("[update ReviewStatistics] : {}", savedReviewStatistic);
    }

    private ReviewStatistics updateReviewStatistics(ReviewStatistics reviewStatistics, ReviewCreateEvent event) {
        double totalRating = reviewStatistics.getReviewCount() * reviewStatistics.getAverageRating() + event.rating();
        long reviews = reviewStatistics.getReviewCount() + 1;
        reviewStatistics.setReviewCount(reviews);
        reviewStatistics.setAverageRating(totalRating / reviews);
        eventPublisher.publish(new ReviewStatisticsUpdateEvent(
                reviewStatistics.getRestaurantId(),
                reviewStatistics.getAverageRating(),
                reviewStatistics.getReviewCount())
        );
        return reviewStatistics;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void createReviewStatistics(RestaurantCreateEvent event) {
        ReviewStatistics reviewStatistics = ReviewStatistics.builder()
                .restaurantId(event.restaurantId())
                .reviewCount(0L)
                .averageRating(0.0)
                .build();
        ReviewStatistics save = reviewStatisticsRepository.save(reviewStatistics);
        log.info("[create ReviewStatistics] : {}", save);
    }

}
