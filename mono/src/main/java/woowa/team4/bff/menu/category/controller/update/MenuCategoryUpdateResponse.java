package woowa.team4.bff.menu.category.controller.update;

import woowa.team4.bff.menu.category.command.MenuCategoryUpdateCommand;

public record MenuCategoryUpdateResponse(String name,
                                         String description) {

    public static MenuCategoryUpdateResponse from(
            final MenuCategoryUpdateCommand menuCategoryUpdateCommand) {
        return new MenuCategoryUpdateResponse(menuCategoryUpdateCommand.name(),
                menuCategoryUpdateCommand.description());
    }
}
