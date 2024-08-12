package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.command.MenuCategoryCreateCommand;
import woowa.team4.bff.menu.category.command.MenuCategoryUpdateCommand;
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
    public String createMenuCategory(final MenuCategoryCreateCommand dto) {
        MenuCategory menuCategory = MenuCategory.create(dto.restaurantUuid(),
                dto.name(), dto.description());
        menuCategoryRepository.save(menuCategory);
        eventPublisher.publishEvent(MenuCategoryCreateEvent.from(menuCategory));
        return menuCategory.getUuid();
    }

    @Transactional
    public MenuCategoryUpdateCommand updateMenuCategory(final MenuCategoryUpdateCommand dto) {
        MenuCategory menuCategory = menuCategoryRepository.findByUuid(dto.uuid())
                .orElseThrow(() -> new MenuCategoryNotFoundException(dto.uuid()));
        menuCategory.update(dto.name(), dto.description());
        eventPublisher.publishEvent(MenuCategoryUpdateEvent.from(menuCategory));
        return MenuCategoryUpdateCommand.from(menuCategory);
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
