package woowa.team4.bff.menu.category.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Getter
@Builder
public class MenuCategoryCreateEvent {

    private final Long id;
    private final String uuid;
    private final String restaurantUuid;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MenuCategoryCreateEvent from(final MenuCategory menuCategory) {
        return MenuCategoryCreateEvent.builder()
                .id(menuCategory.getId())
                .uuid(menuCategory.getUuid())
                .restaurantUuid(menuCategory.getRestaurantUuid())
                .name(menuCategory.getName())
                .description(menuCategory.getDescription())
                .createdAt(menuCategory.getCreatedAt())
                .updatedAt(menuCategory.getUpdatedAt())
                .build();
    }
}
