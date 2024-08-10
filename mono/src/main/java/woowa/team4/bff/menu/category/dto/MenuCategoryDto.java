package woowa.team4.bff.menu.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuCategoryDto {

    private final String restaurantUuid;
    private final String name;
    private final String description;
}
