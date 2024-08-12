package woowa.team4.bff.review.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCreateDto {

    private String restaurantUuid;
    private String content;
    private Double rating;

}
