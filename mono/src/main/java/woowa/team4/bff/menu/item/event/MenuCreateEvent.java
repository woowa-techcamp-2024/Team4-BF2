package woowa.team4.bff.menu.item.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.item.entity.Menu;

@Getter
@Builder
public class MenuCreateEvent {

    private final Long id;
    private final String uuid;
    private final Long menuCategoryId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MenuCreateEvent from(final Menu menu) {
        return MenuCreateEvent.builder()
                .id(menu.getId())
                .uuid(menu.getUuid())
                .menuCategoryId(menu.getMenuCategoryId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }
}
