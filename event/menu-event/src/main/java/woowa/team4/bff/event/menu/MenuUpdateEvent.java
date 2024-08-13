package woowa.team4.bff.event.menu;

import woowa.team4.bff.event.Event;

public record MenuUpdateEvent(Long menuId, String menuName) implements Event {

}
