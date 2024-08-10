package woowa.team4.bff.menu.category.dto.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MenuCategoryUpdateResponse {

    private final String name;
    private final String description;

    public static MenuCategoryUpdateResponse from(
            final MenuCategoryUpdateDto menuCategoryUpdateDto) {
        return new MenuCategoryUpdateResponse(menuCategoryUpdateDto.getName(),
                menuCategoryUpdateDto.getDescription());
    }
}
