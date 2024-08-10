package woowa.team4.bff.menu.category.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuCategoryCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public MenuCategoryCreateDto toDto(final String restaurantUuid) {
        return new MenuCategoryCreateDto(restaurantUuid, name, description);
    }
}
