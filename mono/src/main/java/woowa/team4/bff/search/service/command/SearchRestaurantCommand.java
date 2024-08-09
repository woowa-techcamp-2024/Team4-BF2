package woowa.team4.bff.search.service.command;

public record SearchRestaurantCommand(String restaurantName){
    public static SearchRestaurantCommand of(String restaurantName){
        return new SearchRestaurantCommand(restaurantName);
    }
}
