package woowa.team4.bff.menu.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuCategoryCreateRequest {

    @NotNull
    private String name;
    @NotNull
    private String description;

    public MenuCategoryDto toMenuCategoryDto(final String restaurantUuid) {
        return new MenuCategoryDto(restaurantUuid, name, description);
    }
}
