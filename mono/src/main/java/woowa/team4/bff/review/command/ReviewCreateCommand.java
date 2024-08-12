package woowa.team4.bff.review.command;

import lombok.Builder;

@Builder
public record ReviewCreateCommand (String restaurantUuid, String content, Double rating) {
}
