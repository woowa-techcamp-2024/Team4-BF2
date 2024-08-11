package woowa.team4.bff.search.repository;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.MenuSearchDocument;

public interface MenuSearchRepository extends ElasticsearchRepository<MenuSearchDocument, String> {
    MenuSearchDocument findByMenuId(String menuId);

    List<MenuSearchDocument> findByMenuNameContaining(String menuName);
}