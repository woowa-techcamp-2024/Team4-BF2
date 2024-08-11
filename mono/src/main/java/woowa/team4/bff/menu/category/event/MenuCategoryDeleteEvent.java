package woowa.team4.bff.menu.category.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuCategoryDeleteEvent {

    private final Long id;

    public static MenuCategoryDeleteEvent from(final Long id) {
        return new MenuCategoryDeleteEvent(id);
    }
}
