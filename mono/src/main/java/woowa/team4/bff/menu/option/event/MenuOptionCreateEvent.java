package woowa.team4.bff.menu.option.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.option.entity.MenuOption;
import woowa.team4.bff.menu.option.entity.MenuOptionDetail;

@Getter
@Builder
public class MenuOptionCreateEvent {

    private final Long id;
    private final String uuid;
    private final Long menuId;
    private final String name;
    private final String description;
    private final List<MenuOptionDetailDto> details;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MenuOptionCreateEvent of(final MenuOption menuOption,
            final List<MenuOptionDetail> menuOptionDetails) {
        return MenuOptionCreateEvent.builder()
                .id(menuOption.getId())
                .uuid(menuOption.getUuid())
                .menuId(menuOption.getMenuId())
                .name(menuOption.getName())
                .description(menuOption.getDescription())
                .details(menuOptionDetails.stream()
                        .map(MenuOptionDetailDto::from)
                        .toList())
                .createdAt(menuOption.getCreatedAt())
                .updatedAt(menuOption.getUpdatedAt())
                .build();
    }

    @Getter
    @Builder
    public static class MenuOptionDetailDto {

        private final Long id;
        private final String uuid;
        private final String name;
        private final BigDecimal price;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public static MenuOptionDetailDto from(final MenuOptionDetail menuOptionDetail) {
            return MenuOptionDetailDto.builder()
                    .id(menuOptionDetail.getId())
                    .uuid(menuOptionDetail.getUuid())
                    .name(menuOptionDetail.getName())
                    .price(menuOptionDetail.getPrice())
                    .createdAt(menuOptionDetail.getCreatedAt())
                    .updatedAt(menuOptionDetail.getUpdatedAt())
                    .build();
        }
    }
}
