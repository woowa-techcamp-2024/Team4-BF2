package woowa.team4.bff.menu.item.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Menu {

    private Long id;
    private String uuid;
    private Long menuCategoryId;
    private Long restaurantId;
    private String name;
    private String description;
    private long price;

    public Menu update(final String name, final String description, final long price) {
        return Menu.builder()
                .id(this.id)
                .uuid(this.uuid)
                .menuCategoryId(this.menuCategoryId)
                .restaurantId(this.restaurantId)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
