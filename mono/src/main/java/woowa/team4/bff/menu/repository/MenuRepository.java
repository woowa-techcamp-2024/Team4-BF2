package woowa.team4.bff.menu.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.domain.Menu;

@Repository
public class MenuRepository {
    public List<Menu> findAllByIds(List<String> menuId, String deliveryLocation){
        // ToDo: Menu 필드 보고 select 쿼리 작성
        return null;
    }
}
