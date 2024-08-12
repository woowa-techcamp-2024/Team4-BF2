package woowa.team4.bff.menu.category.controller.update;

import jakarta.validation.constraints.NotBlank;
import woowa.team4.bff.menu.category.command.MenuCategoryUpdateCommand;

public record MenuCategoryUpdateRequest(
        @NotBlank String name,
        @NotBlank String description) {

    public MenuCategoryUpdateCommand toDto(final String menuCategoryUuid) {
        return new MenuCategoryUpdateCommand(menuCategoryUuid, this.name, this.description);
    }
}
