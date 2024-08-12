package woowa.team4.bff.menu.option.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.option.entity.MenuOption;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    Optional<MenuOption> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
