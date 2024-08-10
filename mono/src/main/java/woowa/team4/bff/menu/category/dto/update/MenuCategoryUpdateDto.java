package woowa.team4.bff.menu.category.dto.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Getter
@RequiredArgsConstructor
public class MenuCategoryUpdateDto {

    private final String uuid;
    private final String name;
    private final String description;

    public static MenuCategoryUpdateDto from(final MenuCategory MenuCategory) {
        return new MenuCategoryUpdateDto(MenuCategory.getUuid(),
                MenuCategory.getName(),
                MenuCategory.getDescription());
    }
}
