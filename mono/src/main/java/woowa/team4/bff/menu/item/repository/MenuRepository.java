package woowa.team4.bff.menu.item.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.item.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByUuid(String uuid);

    @Query("SELECT m.id FROM Menu m WHERE m.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") final String uuid);
}
