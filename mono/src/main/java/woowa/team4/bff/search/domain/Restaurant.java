package woowa.team4.bff.search.domain;

import lombok.Builder;

public class Restaurant {
    private String id;
    private String restaurantName;
    private String restaurantId;

    @Builder
    public Restaurant(String restaurantName, String restaurantId) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void update(String restaurantName){
        this.restaurantName = restaurantName;
    }
}
