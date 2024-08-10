package woowa.team4.bff.menu.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Component
public interface JpaMenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
