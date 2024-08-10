package woowa.team4.bff.menu.category.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreatedEvent;

@Component
@RequiredArgsConstructor
public class JpaMenuCategoryEventRepository implements MenuCategoryEventRepository {

    private final JpaMenuCategoryRepository jpaMenuCategoryRepository;

    @Override
    @EventListener
    @Transactional
    public void handleMenuCategoryCreatedEvent(MenuCategoryCreatedEvent event) {
        MenuCategory menuCategory = event.getMenuCategory();
        MenuCategory saveMenuCategory = jpaMenuCategoryRepository.save(menuCategory);
        event.setMenuCategory(saveMenuCategory);
    }
}
