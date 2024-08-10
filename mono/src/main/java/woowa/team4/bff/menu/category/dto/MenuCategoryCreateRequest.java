package woowa.team4.bff.menu.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuCategoryCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public MenuCategoryDto toMenuCategoryDto(final String restaurantUuid) {
        return new MenuCategoryDto(restaurantUuid, name, description);
    }
}
