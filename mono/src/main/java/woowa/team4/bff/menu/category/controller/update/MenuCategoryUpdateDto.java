package woowa.team4.bff.menu.category.controller.update;

import woowa.team4.bff.menu.category.entity.MenuCategory;

public record MenuCategoryUpdateDto(String uuid, String name, String description) {

    public static MenuCategoryUpdateDto from(final MenuCategory menuCategory) {
        return new MenuCategoryUpdateDto(menuCategory.getUuid(),
                menuCategory.getName(),
                menuCategory.getDescription());
    }
}
