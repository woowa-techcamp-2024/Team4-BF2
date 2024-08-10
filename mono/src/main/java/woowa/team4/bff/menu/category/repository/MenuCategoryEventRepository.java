package woowa.team4.bff.menu.category.repository;

import woowa.team4.bff.menu.category.event.MenuCategoryCreatedEvent;

public interface MenuCategoryEventRepository {

    void handleMenuCategoryCreatedEvent(MenuCategoryCreatedEvent event);
}
