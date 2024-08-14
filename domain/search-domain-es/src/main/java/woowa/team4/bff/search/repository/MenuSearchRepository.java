package woowa.team4.bff.search.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.MenuSearchDocument;

import java.util.List;

public interface MenuSearchRepository extends ElasticsearchRepository<MenuSearchDocument, String> {

    MenuSearchDocument findByMenuId(Long menuId);

    @Query("{\"match\": {\"menuName\": \"?0\"}}")
    List<MenuSearchDocument> findByMenuNameContaining(String menuName);
}
