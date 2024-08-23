package woowa.team4.bff.restaurantservice.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.review.command.ReviewCreateCommand;
import woowa.team4.bff.review.command.ReviewMenuCreateCommand;
import woowa.team4.bff.restaurantservice.review.controller.create.ReviewCreateRequest;
import woowa.team4.bff.restaurantservice.review.controller.create.ReviewCreateResponse;
import woowa.team4.bff.review.service.ReviewCreateResult;

import java.util.List;
import woowa.team4.bff.review.service.ReviewService;

import static woowa.team4.bff.common.utils.ApiUtils.success;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{restaurantUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiUtils.ApiResult<ReviewCreateResponse> createReview(@PathVariable final String restaurantUuid,
                                                                 @Valid @RequestBody final ReviewCreateRequest reviewCreateRequest) {

        log.info("[ReviewCreateRequest], {}",reviewCreateRequest);

        List<ReviewMenuCreateCommand> reviewMenus = reviewCreateRequest.menus().stream()
                .map(menu -> ReviewMenuCreateCommand.of(menu.menuUuid(), menu.content(), menu.isLiked()))
                .toList();

        ReviewCreateCommand reviewCreateCommand = ReviewCreateCommand.of(restaurantUuid,
                reviewCreateRequest.content(),
                reviewCreateRequest.rating(),
                reviewMenus);

        ReviewCreateResult reviewCreateResult = reviewService.createReview(reviewCreateCommand);
        return success(ReviewCreateResponse.of(reviewCreateResult.getReviewUuid(), reviewCreateResult.getReviewMenuUuids()));
    }
}
