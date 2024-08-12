package woowa.team4.bff.search.service.command;

public record SearchRestaurantCommand(String keyword, String deliveryLocation) {
    public static SearchRestaurantCommand of(String keyword, String deliveryLocation) {
        return new SearchRestaurantCommand(keyword, deliveryLocation);
    }
}
