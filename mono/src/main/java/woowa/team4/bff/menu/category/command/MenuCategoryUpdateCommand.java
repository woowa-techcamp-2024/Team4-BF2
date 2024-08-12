package woowa.team4.bff.menu.category.command;

import woowa.team4.bff.menu.category.entity.MenuCategory;

public record MenuCategoryUpdateCommand(String uuid, String name, String description) {

    public static MenuCategoryUpdateCommand from(final MenuCategory menuCategory) {
        return new MenuCategoryUpdateCommand(menuCategory.getUuid(),
                menuCategory.getName(),
                menuCategory.getDescription());
    }
}
