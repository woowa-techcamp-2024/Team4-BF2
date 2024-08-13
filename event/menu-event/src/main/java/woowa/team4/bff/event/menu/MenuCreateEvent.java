package woowa.team4.bff.event.menu;

public record MenuCreateEvent(Long menuId, Long restaurantId, String menuName) {
}
