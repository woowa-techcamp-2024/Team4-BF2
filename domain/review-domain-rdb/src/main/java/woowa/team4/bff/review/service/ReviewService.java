package woowa.team4.bff.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.review.command.ReviewCreateCommand;
import woowa.team4.bff.review.domain.Review;
import woowa.team4.bff.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public String createReview(final ReviewCreateCommand reviewCreateCommand) {
        Review review = Review.builder()
                .restaurantUuid(reviewCreateCommand.restaurantUuid())
                .content(reviewCreateCommand.content())
                .rating(reviewCreateCommand.rating())
                .build();

        return reviewRepository.save(review).getReviewUuId();
    }
}
