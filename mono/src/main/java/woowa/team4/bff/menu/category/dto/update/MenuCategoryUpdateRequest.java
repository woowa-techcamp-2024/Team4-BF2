package woowa.team4.bff.menu.category.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuCategoryUpdateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public MenuCategoryUpdateDto toDto(final String menuCategoryUuid) {
        return new MenuCategoryUpdateDto(menuCategoryUuid, name, description);
    }
}
