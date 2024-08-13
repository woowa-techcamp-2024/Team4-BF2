package woowa.team4.bff.review.command;

public record ReviewCreateCommand (String restaurantUuid, String content, Double rating) {

    public static ReviewCreateCommand of(String restaurantUuid, String content, Double rating) {
        return new ReviewCreateCommand(restaurantUuid, content, rating);
    }

}
