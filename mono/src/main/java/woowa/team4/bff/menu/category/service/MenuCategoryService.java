package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.dto.create.MenuCategoryCreateDto;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreateEvent;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final ApplicationEventPublisher eventPublisher;
    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional
    public String createMenuCategory(MenuCategoryCreateDto dto) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setRestaurantUuid(dto.getRestaurantUuid());
        menuCategory.setName(dto.getName());
        menuCategory.setDescription(dto.getDescription());
        menuCategoryRepository.save(menuCategory);
        eventPublisher.publishEvent(MenuCategoryCreateEvent.from(menuCategory));
        return menuCategory.getUuid();
    }
}
