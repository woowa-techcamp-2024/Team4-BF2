package woowa.team4.bff.search.repository;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.MenuSearchDocument;

public interface MenuSearchRepository extends ElasticsearchRepository<MenuSearchDocument, String> {

    MenuSearchDocument findByMenuId(Long menuId);
    List<MenuSearchDocument> findByMenuNameContaining(String menuName);
}
