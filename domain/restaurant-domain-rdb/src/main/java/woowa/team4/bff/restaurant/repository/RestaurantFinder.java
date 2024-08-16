package woowa.team4.bff.restaurant.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.domain.common.utils.PrefixedUuidConverter;
import woowa.team4.bff.restaurant.domain.Restaurant;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantFinder {

    private final RestaurantRepository restaurantRepository;

    public Long findIdByUuid(String uuid) {
        UUID extractedUuid = PrefixedUuidConverter.extractUUID(uuid);
        RestaurantEntity restaurantEntity = restaurantRepository.findByUuid(extractedUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 식당이 존재하지 않습니다: " + uuid));
        return restaurantEntity.getId();
    }
}
