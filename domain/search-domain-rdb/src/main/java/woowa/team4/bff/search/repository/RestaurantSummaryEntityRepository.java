package woowa.team4.bff.search.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.restaurant.entity.RestaurantEntity;

public interface RestaurantSummaryEntityRepository extends JpaRepository<RestaurantEntity, Long> {
    // ToDo: Document 에 배달 가능 위치도 저장하는걸로 바꾸면 해당 메서드 제거
    @Query(
            "SELECT new woowa.team4.bff.domain.RestaurantSummary(r.uuid, r.name, r.image, r.minimumOrderAmount, COALESCE(rs.reviewCount, 0), COALESCE(rs.averageRating, 0.0), CAST(COALESCE(GROUP_CONCAT(m.name), '') AS string)) "
                    + "FROM RestaurantEntity r "
                    + "LEFT JOIN MenuEntity m ON m.restaurantId = r.id "
                    + "LEFT JOIN ReviewStatisticsEntity rs ON rs.restaurantId = r.id "
                    + "WHERE r.id IN :restaurantIds AND r.deliveryLocation = :deliveryLocation "
                    + "GROUP BY r.id, r.uuid, r.name, r.minimumOrderAmount, rs.averageRating, rs.reviewCount"
    )
    List<RestaurantSummary> findByIdsAndDeliveryLocation(@Param("restaurantIds") List<Long> restaurantIds,
                                                              @Param("deliveryLocation") String deliveryLocation);

    @Query("SELECT DISTINCT r.id "
            + "FROM RestaurantEntity r "
            + "LEFT JOIN MenuEntity m ON m.restaurantId = r.id "
            + "WHERE "
            + "r.deliveryLocation = :deliveryLocation AND "
            + "(LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
            + "GROUP BY r.id, r.uuid, r.name, r.minimumOrderAmount")
    List<Long> findIdsByKeywordAndDeliveryLocation(@Param("keyword") String keyword,
                                                   @Param("deliveryLocation") String deliveryLocation,
                                                   Pageable pageable);


    @Query(
            "SELECT new woowa.team4.bff.domain.RestaurantSummary(r.uuid, r.name, r.image, r.minimumOrderAmount, COALESCE(rs.reviewCount, 0), COALESCE(rs.averageRating, 0.0), CAST(COALESCE(GROUP_CONCAT(m.name), '') AS string)) "
                    + "FROM RestaurantEntity r "
                    + "LEFT JOIN MenuEntity m ON m.restaurantId = r.id "
                    + "LEFT JOIN ReviewStatisticsEntity rs ON rs.restaurantId = r.id "
                    + "WHERE r.id IN :restaurantIds "
                    + "GROUP BY r.id, r.uuid, r.name, r.minimumOrderAmount, rs.averageRating, rs.reviewCount"
    )
    List<RestaurantSummary> findByIds(@Param("restaurantIds") List<Long> restaurantIds);


    @Query(
            "SELECT new woowa.team4.bff.domain.RestaurantSummary(r.uuid, r.name, r.image, r.minimumOrderAmount, COALESCE(rs.reviewCount, 0), COALESCE(rs.averageRating, 0.0), CAST(COALESCE(GROUP_CONCAT(m.name), '') AS string)) "
                    + "FROM RestaurantEntity r "
                    + "LEFT JOIN MenuEntity m ON m.restaurantId = r.id "
                    + "LEFT JOIN ReviewStatisticsEntity rs ON rs.restaurantId = r.id "
                    + "WHERE "
                    + "r.deliveryLocation = :deliveryLocation AND "
                    + "(LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
                    + "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
                    + "GROUP BY r.id, r.uuid, r.name, r.minimumOrderAmount, rs.averageRating, rs.reviewCount"
    )
    List<RestaurantSummary> findByKeywordAndDeliveryLocation(@Param("keyword") String keyword,
                                                             @Param("deliveryLocation") String deliveryLocation,
                                                             Pageable pageable);
}
