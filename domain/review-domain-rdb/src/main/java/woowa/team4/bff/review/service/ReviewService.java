package woowa.team4.bff.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.event.review.ReviewCreateEvent;
import woowa.team4.bff.menu.item.repository.MenuRepository;
import woowa.team4.bff.publisher.EventPublisher;
import woowa.team4.bff.restaurant.repository.RestaurantFinder;
import woowa.team4.bff.review.command.ReviewCreateCommand;
import woowa.team4.bff.review.domain.Review;
import woowa.team4.bff.review.domain.ReviewMenu;
import woowa.team4.bff.review.repository.ReviewMenuRepository;
import woowa.team4.bff.review.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMenuRepository reviewMenuRepository;
    private final RestaurantFinder restaurantFinder;
    private final MenuRepository menuRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public ReviewCreateResult createReview(final ReviewCreateCommand reviewCreateCommand) {

        // Uuid를 이용하여 restaurantId(PK)를 찾고, review Entity를 먼저 저장한다.
        Long restaurantId = restaurantFinder.findIdByUuid(reviewCreateCommand.restaurantUuid());
        Review review = Review.builder()
                .restaurantId(restaurantId)
                .content(reviewCreateCommand.content())
                .rating(reviewCreateCommand.rating())
                .build();
        Review savedReview = reviewRepository.save(review);

        // 저장된 review Entity의 pk를 갖고 reviewMenu를 추가한다.
        List<ReviewMenu> reviewMenus = reviewCreateCommand.menus().stream()
                .map(menu -> ReviewMenu.builder()
                        .reviewId(savedReview.getId())
                        .menuId(menuRepository.findByUuid(menu.menuUuid()).getId())
                        .content(menu.content())
                        .isLiked(menu.isRecommend())
                        .build())
                .toList();
        List<ReviewMenu> savedReviewMenus = reviewMenuRepository.saveAll(reviewMenus);

        eventPublisher.publish(new ReviewCreateEvent(restaurantId, savedReview.getRating()));

        log.info("[ReviewMenus] : {}", savedReviewMenus);
        log.info("[Review] : {}", savedReview);

        return ReviewCreateResult.builder()
                .reviewUuid(savedReview.getReviewUuId())
                .reviewMenuUuids(savedReviewMenus.stream()
                        .map(ReviewMenu::getReviewMenuUuid).toList())
                .build();
    }
}
