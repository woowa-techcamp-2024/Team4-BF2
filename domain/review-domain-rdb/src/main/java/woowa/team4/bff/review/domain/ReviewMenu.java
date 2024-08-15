package woowa.team4.bff.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@Getter
@ToString
public class ReviewMenu {

    private final String reviewMenuUuId;
    private final Long reviewId;
    private final Long menuId;
    private final String content;
    private final Boolean isLiked;

}
