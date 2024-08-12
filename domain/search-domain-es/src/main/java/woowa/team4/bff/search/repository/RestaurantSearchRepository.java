package woowa.team4.bff.search.repository;


import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.RestaurantSearchDocument;

public interface RestaurantSearchRepository extends ElasticsearchRepository<RestaurantSearchDocument, String> {

    // {"query": {"match": {"restaurantName": "검색어"}}}
    List<RestaurantSearchDocument> findByRestaurantNameContaining(String restaurantName);

    RestaurantSearchDocument findByRestaurantId(Long restaurantId);
}
