package woowa.team4.bff.menu.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.exception.MenuCategoryNotFoundException;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;
import woowa.team4.bff.menu.item.dto.create.MenuCreateDto;
import woowa.team4.bff.menu.item.dto.update.MenuUpdateDto;
import woowa.team4.bff.menu.item.entity.Menu;
import woowa.team4.bff.menu.item.event.MenuCreateEvent;
import woowa.team4.bff.menu.item.event.MenuDeleteEvent;
import woowa.team4.bff.menu.item.event.MenuUpdateEvent;
import woowa.team4.bff.menu.item.exception.MenuNotFoundException;
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
                .findIdByUuid(dto.menuCategoryUuid())
                .orElseThrow(() -> new MenuCategoryNotFoundException(dto.menuCategoryUuid()));
        Menu menu = Menu.create(menuCategoryId, dto);
        Menu savedMenu = menuRepository.save(menu);
        eventPublisher.publishEvent(MenuCreateEvent.from(savedMenu));
        return savedMenu.getUuid();
    }

    @Transactional
    public MenuUpdateDto updateMenu(final MenuUpdateDto dto) {
        Menu menu = menuRepository.findByUuid(dto.uuid())
                .orElseThrow(() -> new MenuNotFoundException(dto.uuid()));
        menu.update(dto);
        eventPublisher.publishEvent(MenuUpdateEvent.from(menu));
        return MenuUpdateDto.from(menu);
    }

    @Transactional
    public Boolean deleteMenu(final String uuid) {
        Menu menu = menuRepository.findByUuid(uuid)
                .orElseThrow(() -> new MenuNotFoundException(uuid));
        menuRepository.delete(menu);
        eventPublisher.publishEvent(MenuDeleteEvent.from(menu.getId()));
        return Boolean.TRUE;
    }
}
