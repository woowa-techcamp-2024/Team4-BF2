package woowa.team4.bff.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.review.command.ReviewCreateCommand;
import woowa.team4.bff.review.entity.Review;
import woowa.team4.bff.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public String createReview(final ReviewCreateCommand dto) {
        Review review = Review.create(dto.getRestaurantUuid(), dto);
        reviewRepository.save(review);
        return review.getUuid();
    }
}