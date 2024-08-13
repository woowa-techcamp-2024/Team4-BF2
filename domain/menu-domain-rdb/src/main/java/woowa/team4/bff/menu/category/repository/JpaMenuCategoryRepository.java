package woowa.team4.bff.menu.category.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import woowa.team4.bff.menu.category.entity.MenuCategoryEntity;

@Component
public interface JpaMenuCategoryRepository extends JpaRepository<MenuCategoryEntity, Long> {

    Optional<MenuCategoryEntity> findByUuid(final UUID uuid);
}
