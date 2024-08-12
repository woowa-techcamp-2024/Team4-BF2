package woowa.team4.bff.review.controller.create;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import woowa.team4.bff.review.command.ReviewCreateCommand;

public record ReviewCreateRequest(@NotBlank String content,
                                  @DecimalMin(value = "0.0") @DecimalMax(value = "5.0") Double rating) {

    public ReviewCreateCommand toCommand(final String restaurantUuid) {
        return ReviewCreateCommand.builder()
                .restaurantUuid(restaurantUuid)
                .content(content)
                .rating(rating)
                .build();
    }
}