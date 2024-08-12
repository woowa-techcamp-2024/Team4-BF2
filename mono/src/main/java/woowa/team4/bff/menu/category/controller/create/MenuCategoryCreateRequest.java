package woowa.team4.bff.menu.category.controller.create;

import jakarta.validation.constraints.NotBlank;
import woowa.team4.bff.menu.category.command.MenuCategoryCreateCommand;

public record MenuCategoryCreateRequest(
        @NotBlank String name,
        @NotBlank String description) {

    public MenuCategoryCreateCommand toDto(final String restaurantUuid) {
        return new MenuCategoryCreateCommand(restaurantUuid, name, description);
    }
}
