package woowa.team4.bff.menu.category.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.category.entity.MenuCategory;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    Optional<MenuCategory> findByUuid(final String uuid);

    @Query("SELECT mc.id FROM MenuCategory mc WHERE mc.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") final String uuid);
}