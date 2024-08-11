package woowa.team4.bff.menu.category.dto.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuCategoryCreateDto {

    private final String restaurantUuid;
    private final String name;
    private final String description;
}
