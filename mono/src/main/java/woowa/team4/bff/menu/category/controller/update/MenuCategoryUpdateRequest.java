package woowa.team4.bff.menu.category.controller.update;

import jakarta.validation.constraints.NotBlank;

public record MenuCategoryUpdateRequest(
        @NotBlank String name,
        @NotBlank String description) {

    public MenuCategoryUpdateDto toDto(final String menuCategoryUuid) {
        return new MenuCategoryUpdateDto(menuCategoryUuid, this.name, this.description);
    }
}
