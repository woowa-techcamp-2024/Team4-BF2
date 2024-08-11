package woowa.team4.bff.menu.item.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuDeleteEvent {

    private final Long id;

    public static MenuDeleteEvent from(final Long id) {
        return new MenuDeleteEvent(id);
    }
}
