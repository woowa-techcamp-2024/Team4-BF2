package woowa.team4.bff.event.menu;

import woowa.team4.bff.event.Event;

public record MenuDeleteEvent(Long menuId) implements Event {

}
