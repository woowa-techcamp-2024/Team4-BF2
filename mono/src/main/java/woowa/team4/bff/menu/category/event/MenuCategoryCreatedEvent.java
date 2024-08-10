package woowa.team4.bff.menu.category.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Getter
@Setter
@AllArgsConstructor
public class MenuCategoryCreatedEvent {

    private MenuCategory menuCategory;
}
