package woowa.team4.bff.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.review.command.ReviewCreateCommand;
import woowa.team4.bff.review.controller.create.ReviewCreateRequest;
import woowa.team4.bff.review.controller.create.ReviewCreateResponse;
import woowa.team4.bff.review.service.ReviewService;

import static woowa.team4.bff.common.utils.ApiUtils.success;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{restaurantUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiUtils.ApiResult<ReviewCreateResponse> createReview(@PathVariable final String restaurantUuid,
            @Valid @RequestBody final ReviewCreateRequest reviewCreateRequest) {
        ReviewCreateCommand reviewCreateCommand = reviewCreateRequest.toDto(restaurantUuid);
        String reviewUuid = reviewService.createReview(reviewCreateCommand);
        return success(new ReviewCreateResponse(reviewUuid));
    }

}
