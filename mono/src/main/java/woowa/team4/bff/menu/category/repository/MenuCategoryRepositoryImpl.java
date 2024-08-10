package woowa.team4.bff.menu.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreatedEvent;

@Primary
@Repository
@RequiredArgsConstructor
public class MenuCategoryRepositoryImpl implements MenuCategoryRepository {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public MenuCategory save(MenuCategory menuCategory) {
        MenuCategoryCreatedEvent event = new MenuCategoryCreatedEvent(menuCategory);
        eventPublisher.publishEvent(event);
        return event.getMenuCategory();
    }
}
