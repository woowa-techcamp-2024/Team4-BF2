package woowa.team4.bff.menu.item.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.item.entity.Menu;

@Getter
@Builder
public class MenuUpdateEvent {

    private final Long id;
    private final Long menuCategoryId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final LocalDateTime updatedAt;

    public static MenuUpdateEvent from(final Menu menu) {
        return MenuUpdateEvent.builder()
                .id(menu.getId())
                .menuCategoryId(menu.getMenuCategoryId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }
}
