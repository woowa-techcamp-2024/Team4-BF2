package woowa.team4.bff.search.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.document.RestaurantMenusDocument;
import woowa.team4.bff.search.domain.RestaurantMenusSearch;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantMenusRepository {

    private final RestaurantMenusDocumentRepository restaurantMenusDocumentRepository;

    public void save(RestaurantMenusSearch restaurantMenusSearch) {
        RestaurantMenusDocument document = RestaurantMenusDocument.from(restaurantMenusSearch);
        restaurantMenusDocumentRepository.save(document);
    }

    public Optional<RestaurantMenusSearch> findByRestaurantId(Long restaurantId) {
        return restaurantMenusDocumentRepository.findByRestaurantId(restaurantId)
                .map(RestaurantMenusDocument::toDomain);
    }
}
