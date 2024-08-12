package woowa.team4.bff.review.dto.create;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.review.command.ReviewCreateCommand;

@Getter
@Builder
public class ReviewCreateRequest {

    @NotBlank
    private String content;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;

    public ReviewCreateCommand toDto(final String restaurantUuid) {
        return ReviewCreateCommand.builder()
                .restaurantUuid(restaurantUuid)
                .content(content)
                .rating(rating)
                .build();
    }

}
