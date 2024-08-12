package woowa.team4.bff.event.menu;

public record MenuUpdateEvent(Long menuId, Long restaurantId, String menuName) {
}
