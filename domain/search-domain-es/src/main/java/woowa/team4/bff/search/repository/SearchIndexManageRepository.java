package woowa.team4.bff.search.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;

@Repository
@RequiredArgsConstructor
public class SearchIndexManageRepository {

    private final RestaurantSearchRepository restaurantSearchRepository;
    private final MenuSearchRepository menuSearchRepository;
    private final RestaurantSearchMapper restaurantSearchMapper = RestaurantSearchMapper.INSTANCE;
    private final MenuSearchMapper menuSearchMapper = MenuSearchMapper.INSTANCE;

    public Long save(RestaurantSearch restaurantSearch) {
        return restaurantSearchMapper.toDomain(
                        restaurantSearchRepository.save(restaurantSearchMapper.toDocument(restaurantSearch)))
                .getRestaurantId();
    }

    public Long save(MenuSearch menuSearch) {
        return menuSearchMapper.toDomain(menuSearchRepository.save(menuSearchMapper.toDocument(menuSearch)))
                .getMenuId();
    }

    public RestaurantSearch findByRestaurantId(Long restaurantId) {
        return restaurantSearchMapper.toDomain(restaurantSearchRepository.findByRestaurantId(restaurantId));
    }


    public MenuSearch findByMenuId(Long menuId) {
        return menuSearchMapper.toDomain(menuSearchRepository.findByMenuId(menuId));
    }
}
