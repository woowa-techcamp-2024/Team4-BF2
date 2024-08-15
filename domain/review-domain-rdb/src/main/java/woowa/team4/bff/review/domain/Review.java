package woowa.team4.bff.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Review {

    private final Long id;
    private final String reviewUuId;
    private final Long restaurantId;
    private final String content;
    private final Double rating;

}
