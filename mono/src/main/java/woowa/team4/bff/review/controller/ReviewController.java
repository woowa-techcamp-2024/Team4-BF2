package woowa.team4.bff.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.review.dto.create.ReviewCreateDto;
import woowa.team4.bff.review.dto.create.ReviewCreateRequest;
import woowa.team4.bff.review.dto.create.ReviewCreateResponse;
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
        ReviewCreateDto reviewCreateDto = reviewCreateRequest.toDto(restaurantUuid);
        String reviewUuid = reviewService.createReview(reviewCreateDto);
        return success(new ReviewCreateResponse(reviewUuid));
    }

}