package woowa.team4.bff.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
