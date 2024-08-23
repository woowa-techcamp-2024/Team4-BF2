package woowa.team4.bff.search.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.document.RestaurantMenusDocument;

import java.util.Optional;

@Repository
public interface RestaurantMenusDocumentRepository extends ElasticsearchRepository<RestaurantMenusDocument, String> {

    Optional<RestaurantMenusDocument> findByRestaurantId(Long restaurantId);
}
