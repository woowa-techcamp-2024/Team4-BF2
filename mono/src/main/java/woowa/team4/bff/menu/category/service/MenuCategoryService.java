package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.dto.MenuCategoryDto;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreateEvent;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String createMenuCategory(MenuCategoryDto dto) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setRestaurantUuid(dto.getRestaurantUuid());
        menuCategory.setName(dto.getName());
        menuCategory.setDescription(dto.getDescription());
        eventPublisher.publishEvent(new MenuCategoryCreateEvent(menuCategory));
        return menuCategory.getUuid();
    }
}
