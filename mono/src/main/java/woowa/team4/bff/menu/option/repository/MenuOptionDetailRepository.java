package woowa.team4.bff.menu.option.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.option.entity.MenuOptionDetail;

@Repository
public interface MenuOptionDetailRepository extends JpaRepository<MenuOptionDetail, Long> {

    List<MenuOptionDetail> findByMenuOptionId(Long menuOptionId);

    Optional<MenuOptionDetail> findByUuid(String uuid);

    void deleteByMenuOptionId(Long menuOptionId);
}
