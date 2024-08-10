package woowa.team4.bff.menu.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.category.dto.MenuCategoryDto;
import woowa.team4.bff.menu.category.entity.MenuCategory;
import woowa.team4.bff.menu.category.repository.MenuCategoryRepository;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    public String createMenuCategory(MenuCategoryDto dto) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setRestaurantUuid(dto.getRestaurantUuid());
        menuCategory.setName(dto.getName());
        menuCategory.setDescription(dto.getDescription());
        menuCategoryRepository.save(menuCategory);
        return menuCategory.getUuid();
    }
}
