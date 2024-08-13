package woowa.team4.bff.review.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Review {

    private final String reviewId;
    private final String restaurantUuid;
    private final String content;
    private final Double rating;

}
