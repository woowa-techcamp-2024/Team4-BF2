package woowa.team4.bff.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowa.team4.bff.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}