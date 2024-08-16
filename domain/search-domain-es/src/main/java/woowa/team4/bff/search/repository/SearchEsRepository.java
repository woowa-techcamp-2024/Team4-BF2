package woowa.team4.bff.search.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;

@Repository
@RequiredArgsConstructor
public class SearchEsRepository {
    private final RestaurantSearchRepository restaurantSearchRepository;
    private final MenuSearchRepository menuSearchRepository;
    private final RestaurantSearchMapper restaurantSearchMapper = RestaurantSearchMapper.INSTANCE;
    private final MenuSearchMapper menuSearchMapper = MenuSearchMapper.INSTANCE;

    public List<RestaurantSearch> findAllByRestaurantName(String restaurantName) {
        return restaurantSearchRepository.findByRestaurantNameContaining(restaurantName).stream()
                .map(restaurantSearchMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<MenuSearch> findAllByMenuName(String menuName) {
        return menuSearchRepository.findByMenuNameContaining(menuName).stream()
                .map(menuSearchMapper::toDomain)
                .collect(Collectors.toList());
    }
}
