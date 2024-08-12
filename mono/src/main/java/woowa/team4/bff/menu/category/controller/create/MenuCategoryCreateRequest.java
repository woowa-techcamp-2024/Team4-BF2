package woowa.team4.bff.menu.category.controller.create;

import jakarta.validation.constraints.NotBlank;

public record MenuCategoryCreateRequest(
        @NotBlank String name,
        @NotBlank String description) {

    public MenuCategoryCreateDto toDto(final String restaurantUuid) {
        return new MenuCategoryCreateDto(restaurantUuid, name, description);
    }
}
