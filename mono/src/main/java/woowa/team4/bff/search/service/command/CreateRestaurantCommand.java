package woowa.team4.bff.search.service.command;

public record CreateRestaurantCommand(String restaurantId, String restaurantName) {
    public static CreateRestaurantCommand of(String restaurantId, String restaurantName){
        return new CreateRestaurantCommand(restaurantId, restaurantName);
    }
}
