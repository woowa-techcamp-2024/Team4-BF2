package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.dto.create.MenuCategoryCreateDto;
import woowa.team4.bff.menu.category.dto.update.MenuCategoryUpdateDto;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.event.MenuCategoryCreateEvent;
import woowa.team4.bff.menu.category.event.MenuCategoryDeleteEvent;
import woowa.team4.bff.menu.category.event.MenuCategoryUpdateEvent;
import woowa.team4.bff.menu.category.exception.MenuCategoryNotFoundException;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final ApplicationEventPublisher eventPublisher;
    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional
    public String createMenuCategory(final MenuCategoryCreateDto dto) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setRestaurantUuid(dto.getRestaurantUuid());
        menuCategory.setName(dto.getName());
        menuCategory.setDescription(dto.getDescription());
        menuCategoryRepository.save(menuCategory);
        eventPublisher.publishEvent(MenuCategoryCreateEvent.from(menuCategory));
        return menuCategory.getUuid();
    }

    @Transactional
    public MenuCategoryUpdateDto updateMenuCategory(final MenuCategoryUpdateDto dto) {
        MenuCategory menuCategory = menuCategoryRepository.findByUuid(dto.getUuid())
                .orElseThrow(() -> new MenuCategoryNotFoundException(dto.getUuid()));
        menuCategory.setName(dto.getName());
        menuCategory.setDescription(dto.getDescription());
        eventPublisher.publishEvent(MenuCategoryUpdateEvent.from(menuCategory));
        return MenuCategoryUpdateDto.from(menuCategory);
    }

    @Transactional
    public Boolean deleteMenuCategory(final String uuid) {
        MenuCategory menuCategory = menuCategoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new MenuCategoryNotFoundException(uuid));
        menuCategoryRepository.delete(menuCategory);
        eventPublisher.publishEvent(MenuCategoryDeleteEvent.from(menuCategory.getId()));
        return Boolean.TRUE;
    }
}
