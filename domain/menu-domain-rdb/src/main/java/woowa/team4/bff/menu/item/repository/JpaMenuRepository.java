package woowa.team4.bff.menu.item.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import woowa.team4.bff.menu.item.entity.MenuEntity;

@Component
public interface JpaMenuRepository extends JpaRepository<MenuEntity, Long> {

    Optional<MenuEntity> findByUuid(UUID uuid);
}
