package woowa.team4.bff.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ReviewStatistics {

    private Long id;
    private Long restaurantId;
    private Long reviewCount;
    private Double averageRating;

}
