package woowa.team4.bff.review.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import woowa.team4.bff.review.entity.ReviewStatisticsEntity;

import java.util.Optional;

public interface ReviewStatisticsEntityRepository extends JpaRepository<ReviewStatisticsEntity, Long> {

    Optional<ReviewStatisticsEntity> findByRestaurantId(Long restaurantId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReviewStatisticsEntity r WHERE r.restaurantId = :restaurantId")
    Optional<ReviewStatisticsEntity> findByRestaurantIdUsingPessimisticLock(Long restaurantId);

}
