package woowa.team4.bff.menu.category.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuCategory {

    private Long id;
    private String uuid;
    private Long restaurantId;
    private String name;
    private String description;

    public MenuCategory update(String name, String description) {
        return MenuCategory.builder()
                .id(this.id)
                .uuid(this.uuid)
                .restaurantId(this.restaurantId)
                .name(name)
                .description(description)
                .build();
    }
}
