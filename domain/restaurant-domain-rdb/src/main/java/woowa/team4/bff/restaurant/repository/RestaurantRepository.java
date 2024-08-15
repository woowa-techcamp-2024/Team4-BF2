package woowa.team4.bff.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByUuid(UUID uuid);
    List<RestaurantEntity> findByNameContaining(String restaurantName);
}
