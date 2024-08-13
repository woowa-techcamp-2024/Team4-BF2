package woowa.team4.bff.search.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;
import woowa.team4.bff.search.domain.RestaurantSearchResult;

public interface RestaurantEntityRepository extends JpaRepository<RestaurantEntity, Long> {
    // ToDo: Restauarnt, Menu, ReviewStatus 관련 module 완성시 사용
    @Query("SELECT new woowa.team4.bff.search.domain.RestaurantSearchResult(r.uuid, r.name, r.minimumOrderAmount, " +
            "rs.rating, rs.reviewCount, m.menuName) " +
            "FROM Restaurant r " +
            "LEFT JOIN RestaurantReviewStatus rs ON r.id = rs.restaurantId " +
            "LEFT JOIN Menu m ON r.id = m.restaurantId " +
            "WHERE r.id IN :restaurantIds and r.deliveryLocation = :deliveryLocation")
    List<RestaurantSearchResult> findRestaurantSearchResults(@Param("restaurantIds") List<Long> restaurantIds, @Param("deliveryLocation") String deliveryLocation);
}
