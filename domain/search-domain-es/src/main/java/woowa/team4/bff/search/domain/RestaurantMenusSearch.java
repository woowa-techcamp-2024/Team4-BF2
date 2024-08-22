package woowa.team4.bff.search.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
public class RestaurantMenusSearch {

    private String id;
    private String restaurantName;
    private String deliveryLocation;
    private Long restaurantId;
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    public void updateRestaurantName(String newName) {
        this.restaurantName = newName;
    }

    public void addMenu(Menu menu) {
        this.menus.add(menu);
    }

    @Getter
    @Builder
    public static class Menu {

        private String menuName;
        private Long menuId;

        public void updateMenuName(String newName) {
            this.menuName = newName;
        }
    }
}
