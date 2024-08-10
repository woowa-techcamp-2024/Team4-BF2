package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreateEvent;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;

@Service
@RequiredArgsConstructor
public class MenuCategoryEventListener {

    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional
    @EventListener(MenuCategoryCreateEvent.class)
    public void handleMenuCategoryCreatedEvent(MenuCategoryCreateEvent event) {
        MenuCategory menuCategory = event.getMenuCategory();
        menuCategoryRepository.save(menuCategory);
    }
}
