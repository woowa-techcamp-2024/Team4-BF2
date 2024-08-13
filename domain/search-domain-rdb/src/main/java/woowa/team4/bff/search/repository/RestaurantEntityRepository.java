package woowa.team4.bff.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantEntityRepository {
    // ToDo: Restauarnt, Menu, ReviewStatus 관련 module 완성시 사용
//    @Query("SELECT new woowa.team4.bff.search.domain.RestaurantSearchResult(r.uuid, r.name, r.minimumOrderAmount, " +
//            "rs.rating, rs.reviewCount, m.menuName) " +
//            "FROM Restaurant r " +
//            "LEFT JOIN RestaurantReviewStatus rs ON r.id = rs.restaurantId " +
//            "LEFT JOIN Menu m ON r.id = m.restaurantId " +
//            "WHERE r.id IN :restaurantIds and r.deliveryLocation = :deliveryLocation")
//    List<RestaurantSearchResult> findRestaurantSearchResults(@Param("restaurantIds") List<Long> restaurantIds, @Param("deliveryLocation") String deliveryLocation);
}
