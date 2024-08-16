package woowa.team4.bff.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowa.team4.bff.review.entity.ReviewStatisticsEntity;

import java.util.Optional;

public interface ReviewStatisticsEntityRepository extends JpaRepository<ReviewStatisticsEntity, Long> {

    Optional<ReviewStatisticsEntity> findByRestaurantId(Long restaurantId);

}
