package woowa.team4.bff.menu.category.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Getter
@Builder
public class MenuCategoryUpdateEvent {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime updatedAt;

    public static MenuCategoryUpdateEvent from(final MenuCategory menuCategory) {
        return MenuCategoryUpdateEvent.builder()
                .id(menuCategory.getId())
                .name(menuCategory.getName())
                .description(menuCategory.getDescription())
                .updatedAt(menuCategory.getUpdatedAt())
                .build();
    }
}
