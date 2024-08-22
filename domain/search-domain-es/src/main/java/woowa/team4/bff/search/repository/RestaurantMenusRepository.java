package woowa.team4.bff.search.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.document.RestaurantMenusDocument;
import woowa.team4.bff.search.document.RestaurantMenusDocument.MenuDocument;
import woowa.team4.bff.search.domain.RestaurantMenusSearch;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantMenusRepository {

    private final RestaurantMenusDocumentRepository restaurantMenusDocumentRepository;

    public void save(RestaurantMenusSearch restaurantMenusSearch) {
        RestaurantMenusDocument document = RestaurantMenusDocument.builder()
                .id(restaurantMenusSearch.getId())
                .restaurantName(restaurantMenusSearch.getRestaurantName())
                .deliveryLocation(restaurantMenusSearch.getDeliveryLocation())
                .restaurantId(restaurantMenusSearch.getRestaurantId())
                .menus(restaurantMenusSearch.getMenus()
                        .stream()
                        .map(MenuDocument::from)
                        .toList())
                .build();
        restaurantMenusDocumentRepository.save(document);
    }

    public Optional<RestaurantMenusSearch> findByRestaurantId(Long restaurantId) {
        return restaurantMenusDocumentRepository.findByRestaurantId(restaurantId)
                .map(document -> RestaurantMenusSearch.builder()
                        .id(document.getId())
                        .restaurantName(document.getRestaurantName())
                        .deliveryLocation(document.getDeliveryLocation())
                        .restaurantId(document.getRestaurantId())
                        .menus(document.getMenus()
                                .stream()
                                .map(MenuDocument::toDomain)
                                .toList())
                        .build());
    }
}
