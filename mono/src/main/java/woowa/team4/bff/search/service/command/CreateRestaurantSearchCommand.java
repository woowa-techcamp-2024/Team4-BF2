package woowa.team4.bff.search.service.command;

public record CreateRestaurantSearchCommand(String restaurantId, String restaurantName) {
    public static CreateRestaurantSearchCommand of(String restaurantId, String restaurantName){
        return new CreateRestaurantSearchCommand(restaurantId, restaurantName);
    }
}
