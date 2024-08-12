package woowa.team4.bff.menu.option.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuOptionDeleteEvent {

    private final Long id;

    public static MenuOptionDeleteEvent from(final Long id) {
        return new MenuOptionDeleteEvent(id);
    }
}
