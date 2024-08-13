package woowa.team4.bff.menu.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.command.MenuCategoryCreateCommand;
import woowa.team4.bff.menu.category.command.MenuCategoryUpdateCommand;
import woowa.team4.bff.menu.category.domain.MenuCategory;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;
import woowa.team4.bff.restaurant.repository.RestaurantFinder;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final RestaurantFinder restaurantFinder;
    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional
    public String createMenuCategory(final MenuCategoryCreateCommand command) {
        Long restaurantId = restaurantFinder.findIdByUuid(command.restaurantUuid());
        MenuCategory menuCategory = MenuCategory.builder()
                .restaurantId(restaurantId)
                .name(command.name())
                .description(command.description())
                .build();
        MenuCategory savedMenuCategory = menuCategoryRepository.save(menuCategory);
        return savedMenuCategory.getUuid();
    }

    @Transactional
    public String updateMenuCategory(
            final MenuCategoryUpdateCommand command) {
        MenuCategory menuCategory = menuCategoryRepository.findByUuid(command.uuid())
                .update(command.name(), command.description());
        MenuCategory updatedMenuCategory = menuCategoryRepository.save(menuCategory);
        return updatedMenuCategory.getUuid();
    }

    @Transactional
    public String deleteMenuCategory(final String uuid) {
        MenuCategory menuCategory = menuCategoryRepository.findByUuid(uuid);
        menuCategoryRepository.delete(menuCategory);
        return menuCategory.getUuid();
    }
}
