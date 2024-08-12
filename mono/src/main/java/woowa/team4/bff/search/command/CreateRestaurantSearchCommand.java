package woowa.team4.bff.search.command;

public record CreateRestaurantSearchCommand(Long restaurantId, String restaurantName) {
    public static CreateRestaurantSearchCommand of(Long restaurantId, String restaurantName) {
        return new CreateRestaurantSearchCommand(restaurantId, restaurantName);
    }
}
