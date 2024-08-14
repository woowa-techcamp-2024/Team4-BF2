package woowa.team4.bff.search.repository;


import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.RestaurantSearchDocument;

import java.util.List;

public interface RestaurantSearchRepository extends ElasticsearchRepository<RestaurantSearchDocument, String> {

    // {"query": {"match": {"restaurantName": "검색어"}}}
    @Query("{\"match\": {\"restaurantName\": \"?0\"}}")
    List<RestaurantSearchDocument> findByRestaurantNameContaining(String restaurantName);

    RestaurantSearchDocument findByRestaurantId(Long restaurantId);
}
