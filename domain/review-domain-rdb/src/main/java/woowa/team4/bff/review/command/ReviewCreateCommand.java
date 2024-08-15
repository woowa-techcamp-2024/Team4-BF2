package woowa.team4.bff.review.command;

import java.util.List;

public record ReviewCreateCommand (String restaurantUuid, String content, Double rating, List<ReviewMenuCreateCommand> menus) {

    public static ReviewCreateCommand of(String restaurantUuid, String content, Double rating, List<ReviewMenuCreateCommand> menus) {
        return new ReviewCreateCommand(restaurantUuid, content, rating, menus);
    }

}
