package woowa.team4.bff.menu.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.exception.MenuCategoryNotFoundException;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;
import woowa.team4.bff.menu.item.dto.create.MenuCreateDto;
import woowa.team4.bff.menu.item.entity.Menu;
import woowa.team4.bff.menu.item.event.MenuCreateEvent;
import woowa.team4.bff.menu.item.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final ApplicationEventPublisher eventPublisher;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public String createMenu(final MenuCreateDto dto) {
        Long menuCategoryId = menuCategoryRepository
                .findIdByUuid(dto.getMenuCategoryUuid())
                .orElseThrow(() -> new MenuCategoryNotFoundException(dto.getMenuCategoryUuid()));
        Menu menu = Menu.create(menuCategoryId, dto);
        Menu savedMenu = menuRepository.save(menu);
        eventPublisher.publishEvent(MenuCreateEvent.from(savedMenu));
        return savedMenu.getUuid();
    }
}
