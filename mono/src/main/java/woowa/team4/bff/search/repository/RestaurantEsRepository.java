package woowa.team4.bff.search.repository;

import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import woowa.team4.bff.search.document.RestaurantDocument;

public interface RestaurantEsRepository extends ElasticsearchRepository<RestaurantDocument, String> {
    List<RestaurantDocument> findByRestaurantNameContaining(String restaurantName);

    RestaurantDocument findByRestaurantId(String restaurantId);
}
