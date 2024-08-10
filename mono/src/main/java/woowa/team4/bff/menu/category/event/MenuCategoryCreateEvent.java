package woowa.team4.bff.menu.category.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Getter
@RequiredArgsConstructor
public class MenuCategoryCreateEvent {

    private final MenuCategory menuCategory;
}
