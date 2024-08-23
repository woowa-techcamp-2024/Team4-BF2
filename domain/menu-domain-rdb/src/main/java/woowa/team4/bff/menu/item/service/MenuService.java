package woowa.team4.bff.menu.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.event.menu.MenuCreateEvent;
import woowa.team4.bff.event.menu.MenuDeleteEvent;
import woowa.team4.bff.event.menu.MenuUpdateEvent;
import woowa.team4.bff.menu.category.domain.MenuCategory;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;
import woowa.team4.bff.menu.item.command.MenuCreateCommand;
import woowa.team4.bff.menu.item.command.MenuUpdateCommand;
import woowa.team4.bff.menu.item.domain.Menu;
import woowa.team4.bff.menu.item.repository.MenuRepository;
import woowa.team4.bff.publisher.EventPublisher;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final EventPublisher eventPublisher;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public String createMenu(final MenuCreateCommand createCommand) {
        MenuCategory menuCategory = menuCategoryRepository
                .findByUuid(createCommand.menuCategoryUuid());
        Menu menu = Menu.builder()
                .menuCategoryId(menuCategory.getId())
                .restaurantId(menuCategory.getRestaurantId())
                .name(createCommand.name())
                .description(createCommand.description())
                .price(createCommand.price())
                .build();
        Menu savedMenu = menuRepository.save(menu);
        // 이벤트 발행
        eventPublisher.publish(
                new MenuCreateEvent(savedMenu.getId(),
                        savedMenu.getRestaurantId(),
                        savedMenu.getName()));
        return savedMenu.getUuid();
    }

    @Transactional
    public String updateMenu(final MenuUpdateCommand updateCommand) {
        Menu menu = menuRepository.findByUuid(updateCommand.uuid())
                .update(updateCommand.name(), updateCommand.description(), updateCommand.price());
        Menu updatedMenu = menuRepository.save(menu);
        // 이벤트 발행
        eventPublisher.publish(
                new MenuUpdateEvent(updatedMenu.getId(), updatedMenu.getName(), menu.getRestaurantId()));
        return updatedMenu.getUuid();
    }

    @Transactional
    public String deleteMenu(final String uuid) {
        Menu menu = menuRepository.findByUuid(uuid);
        menuRepository.delete(menu);
        // 이벤트 발행
        eventPublisher.publish(new MenuDeleteEvent(menu.getId()));
        return menu.getUuid();
    }
}
