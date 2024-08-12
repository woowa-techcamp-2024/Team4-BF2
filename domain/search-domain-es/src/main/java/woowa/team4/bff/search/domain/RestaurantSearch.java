package woowa.team4.bff.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantSearch {

    private String id;
    private String restaurantName;
    private Long restaurantId;

    public void update(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
